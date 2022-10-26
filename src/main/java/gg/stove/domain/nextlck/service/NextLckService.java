package gg.stove.domain.nextlck.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;
import gg.stove.cache.annotation.RedisCacheable;
import gg.stove.domain.nextlck.dto.NextLckRoasterRequest;
import gg.stove.domain.nextlck.dto.NextLckRoasterResponse;
import gg.stove.domain.nextlck.dto.NextLckSaveRequest;
import gg.stove.domain.nextlck.dto.NextLckViewResponse;
import gg.stove.domain.nextlck.dto.ParticipantsCountResponseView;
import gg.stove.domain.nextlck.entity.NextLckEntity;
import gg.stove.domain.nextlck.repository.NextLckRepository;
import gg.stove.domain.progamer.entity.ProgamerEntity;
import gg.stove.domain.progamer.repository.ProgamerRepository;
import gg.stove.domain.team.entity.TeamEntity;
import gg.stove.domain.team.repository.TeamRepository;
import gg.stove.domain.user.entity.UserEntity;
import gg.stove.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NextLckService {
    private final NextLckRepository nextLckRepository;
    private final ProgamerRepository progamerRepository;
    private final TeamRepository teamRepository;
    private final UserRepository userRepository;

    public NextLckViewResponse loadNextLck(Long userId) {
        UserEntity userEntity = userRepository.findById(userId).orElseThrow();
        List<NextLckEntity> nextLckEntities = nextLckRepository.findAllByUserWithTeamAndPlayers(userEntity);
        return NextLckViewResponse.of(
            nextLckEntities.stream()
                .map(NextLckRoasterResponse::from)
                .collect(Collectors.toList())
        );
    }

    @Transactional
    public void saveNextLck(Long userId, NextLckSaveRequest request) {
        UserEntity user = userRepository.findById(userId).orElseThrow();

        List<NextLckRoasterRequest> teamWithPlayers = request.getRoasters();
        if (teamWithPlayers.size() > 10) {
            throw new IllegalArgumentException();
        }

        Map<Long, ProgamerEntity> progamerEntityMap = progamerRepository.findAll().stream()
            .collect(Collectors.toMap(ProgamerEntity::getId, progamer -> progamer));
        Map<Long, TeamEntity> teamEntityMap = teamRepository.findAll().stream()
            .collect(Collectors.toMap(TeamEntity::getId, progamer -> progamer));
        Map<TeamEntity, NextLckEntity> teamAndNextLckMap = nextLckRepository.findAllByUserWithTeam(user).stream()
            .collect(Collectors.toMap(NextLckEntity::getTeam, nextLck -> nextLck));

        Set<Long> teamIdSet = new HashSet<>();
        List<NextLckEntity> createNextLckEntities = new ArrayList<>();

        for (NextLckRoasterRequest teamWithPlayer : teamWithPlayers) {
            Long teamId = teamWithPlayer.getTeamId();
            if (teamIdSet.contains(teamId)) {
                throw new IllegalArgumentException();
            }
            teamIdSet.add(teamId);
            TeamEntity team = teamEntityMap.get(teamId);

            ProgamerEntity top = progamerEntityMap.get(teamWithPlayer.getTopId());
            ProgamerEntity jgl = progamerEntityMap.get(teamWithPlayer.getJglId());
            ProgamerEntity mid = progamerEntityMap.get(teamWithPlayer.getMidId());
            ProgamerEntity bot = progamerEntityMap.get(teamWithPlayer.getBotId());
            ProgamerEntity spt = progamerEntityMap.get(teamWithPlayer.getSptId());

            if (teamAndNextLckMap.containsKey(team)) {
                teamAndNextLckMap.get(team).update(top, jgl, mid, bot, spt);
            } else {
                createNextLckEntities.add(
                    NextLckEntity.builder()
                        .user(user)
                        .team(team)
                        .top(top)
                        .jgl(jgl)
                        .mid(mid)
                        .bot(bot)
                        .spt(spt)
                        .build()
                );
            }
        }

        nextLckRepository.saveAll(createNextLckEntities);
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
