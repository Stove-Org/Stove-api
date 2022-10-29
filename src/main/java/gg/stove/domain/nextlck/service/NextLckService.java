package gg.stove.domain.nextlck.service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;
import com.mysema.commons.lang.Pair;
import gg.stove.cache.annotation.RedisCacheable;
import gg.stove.domain.nextlck.dto.NextLckSaveRequest;
import gg.stove.domain.nextlck.dto.NextLckViewResponse;
import gg.stove.domain.nextlck.dto.ParticipantsCountResponseView;
import gg.stove.domain.nextlck.entity.NextLckEntity;
import gg.stove.domain.nextlck.repository.NextLckRepository;
import gg.stove.domain.progamer.entity.Position;
import gg.stove.domain.progamer.entity.ProgamerEntity;
import gg.stove.domain.progamer.repository.ProgamerRepository;
import gg.stove.domain.team.Team;
import gg.stove.domain.user.entity.UserEntity;
import gg.stove.domain.user.repository.UserRepository;
import gg.stove.exception.DataNotFoundException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import static gg.stove.utils.EnumUser.DEFAULT_USER;

@Service
@RequiredArgsConstructor
public class NextLckService {
    private final NextLckRepository nextLckRepository;
    private final ProgamerRepository progamerRepository;
    private final UserRepository userRepository;

    public List<NextLckViewResponse> loadNextLck(Long userId) {
        UserEntity userEntity = userRepository.findById(userId).orElseThrow();
        List<NextLckEntity> nextLckEntities = nextLckRepository.findAllByUserWithPlayers(userEntity);

        if (nextLckEntities.isEmpty()) {
            UserEntity defaultUser = userRepository.findById(DEFAULT_USER.getId()).orElseThrow();
            nextLckEntities = nextLckRepository.findAllByUserWithPlayers(defaultUser);
        }

        return nextLckEntities.stream()
            .map(NextLckViewResponse::of)
            .collect(Collectors.toList());
    }

    public List<NextLckViewResponse> loadNextLck(@NonNull String nickname) {
        UserEntity userEntity = userRepository.findByNickname(nickname).orElseThrow(
            () -> new DataNotFoundException("존재하지 않는 nickname입니다.")
        );
        List<NextLckEntity> nextLckEntities = nextLckRepository.findAllByUserWithPlayers(userEntity);

        return nextLckEntities.stream()
            .map(NextLckViewResponse::of)
            .collect(Collectors.toList());
    }

    @Transactional
    public void saveNextLck(Long userId, List<NextLckSaveRequest> request) {
        UserEntity user = userRepository.findById(userId).orElseThrow();

        Map<Pair<Team, Position>, NextLckEntity> nextLckEntityMap = nextLckRepository.findAllByUser(user).stream()
            .collect(Collectors.toMap(nextLck -> new Pair<>(Team.of(nextLck.getTeamId()), nextLck.getPosition()), nextLck -> nextLck));

        Set<NextLckEntity> insertNextLcks = new HashSet<>();

        Map<Long, ProgamerEntity> progamerEntityMap = progamerRepository.findAll().stream()
            .collect(Collectors.toMap(ProgamerEntity::getId, progamer -> progamer));

        for (NextLckSaveRequest nextLckSaveRequest : request) {
            Position position = nextLckSaveRequest.getPosition();
            Team team = nextLckSaveRequest.getTeam();
            Long progamerId = nextLckSaveRequest.getProgamerId();
            ProgamerEntity progamerEntity = progamerEntityMap.get(progamerId);

            if (progamerEntity == null) {
                throw new DataNotFoundException("progamerId: " + progamerId + "에 해당하는 데이터가 존재하지 않습니다.");
            }

            Pair<Team, Position> teamAndPositionKey = new Pair<>(team, position);
            if (nextLckEntityMap.containsKey(teamAndPositionKey)) {
                nextLckEntityMap.get(teamAndPositionKey).update(progamerEntity);
            } else {
                insertNextLcks.add(
                    NextLckEntity.builder()
                        .user(user)
                        .position(position)
                        .teamId(team.getId())
                        .progamer(progamerEntity)
                        .build()
                );
            }
        }

        nextLckRepository.saveAll(insertNextLcks);
    }

    @Transactional
    public void resetNextLck(Long id) {
        UserEntity user = userRepository.findById(id).orElseThrow();
        List<NextLckEntity> nextLckEntities = nextLckRepository.findAllByUser(user);
        nextLckRepository.deleteAll(nextLckEntities);
    }
    @RedisCacheable(key = "ParticipantsCountResponseView.getParticipantsCount", expireSecond = 60L)
    public ParticipantsCountResponseView getParticipantsCount() {
        long count = nextLckRepository.countDistinctUser();
        return new ParticipantsCountResponseView(count);
    }
}
