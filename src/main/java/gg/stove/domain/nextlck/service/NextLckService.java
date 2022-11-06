package gg.stove.domain.nextlck.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import gg.stove.cache.annotation.RedisCacheable;
import gg.stove.domain.nextlck.dto.NextLckRankingProgamerView;
import gg.stove.domain.nextlck.dto.NextLckRankingViewResponse;
import gg.stove.domain.nextlck.dto.NextLckRankingViewResponseItem;
import gg.stove.domain.nextlck.dto.NextLckSaveRequest;
import gg.stove.domain.nextlck.dto.NextLckViewResponse;
import gg.stove.domain.nextlck.dto.ParticipantsCountResponseView;
import gg.stove.domain.nextlck.dto.TeamAndPositionKey;
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

        return NextLckViewResponse.of(nextLckEntities);
    }

    public List<NextLckViewResponse> loadNextLck(@NonNull String nickname) {
        UserEntity userEntity = userRepository.findByNickname(nickname).orElseThrow(
            () -> new DataNotFoundException("존재하지 않는 nickname입니다.")
        );
        List<NextLckEntity> nextLckEntities = nextLckRepository.findAllByUserWithPlayers(userEntity);
        return NextLckViewResponse.of(nextLckEntities);
    }

    @Transactional
    public void saveNextLck(Long userId, List<NextLckSaveRequest> request) {
        UserEntity user = userRepository.findById(userId).orElseThrow();

        List<NextLckEntity> nextLckEntities = nextLckRepository.findAllByUser(user);
        Map<TeamAndPositionKey, NextLckEntity> nextLckEntityMap = nextLckEntities.stream()
            .collect(Collectors.toMap(nextLck -> new TeamAndPositionKey(Team.of(nextLck.getTeamId()), nextLck.getPosition()), nextLck -> nextLck));

        Map<TeamAndPositionKey, NextLckEntity> deleteNextLck = nextLckEntities.stream()
            .collect(Collectors.toMap(nextLck -> new TeamAndPositionKey(Team.of(nextLck.getTeamId()), nextLck.getPosition()), nextLck -> nextLck));

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

            TeamAndPositionKey teamAndPositionKey = new TeamAndPositionKey(team, position);
            if (nextLckEntityMap.containsKey(teamAndPositionKey)) {
                nextLckEntityMap.get(teamAndPositionKey).update(progamerEntity);
                deleteNextLck.remove(teamAndPositionKey);
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

        nextLckRepository.deleteAll(deleteNextLck.values());
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

    @RedisCacheable(key = "ParticipantsCountResponseView.getNextLckRanking", expireSecond = 1800L)
    public List<NextLckRankingViewResponse> getNextLckRanking() {
        List<NextLckEntity> nextLckEntities = nextLckRepository.findAllWithPlayers();

        Map<TeamAndPositionKey, List<ProgamerEntity>> map = new HashMap<>();
        for (NextLckEntity nextLckEntity : nextLckEntities) {
            Team team = Team.of(nextLckEntity.getTeamId());
            Position position = nextLckEntity.getPosition();

            TeamAndPositionKey key = new TeamAndPositionKey(team, position);

            if (!map.containsKey(key)) {
                map.put(key, new ArrayList<>());
            }

            map.get(key).add(nextLckEntity.getProgamer());
        }

        List<Team> teams = List.of(Team.DRX, Team.T1, Team.GEN, Team.DK, Team.LSB, Team.KT, Team.KDF, Team.NS, Team.BRO, Team.HLE);
        List<Position> positions = List.of(Position.TOP, Position.JGL, Position.MID, Position.BOT, Position.SPT);

        List<NextLckRankingViewResponse> results = new ArrayList<>();
        for (Team team : teams) {
            for (Position pos : positions) {
                TeamAndPositionKey key = new TeamAndPositionKey(team, pos);
                List<ProgamerEntity> progamerEntities = map.get(key);

                if (key.equals(new TeamAndPositionKey(Team.DRX, Position.TOP))) {
                    System.out.println();
                }

                Map<ProgamerEntity, Long> progamerCountMap = new HashMap<>();
                for (ProgamerEntity progamerEntity : progamerEntities) {
                    progamerCountMap.put(progamerEntity, progamerCountMap.getOrDefault(progamerEntity, 0L)+1);
                }

                LinkedList<Entry<ProgamerEntity, Long>> entryList = new LinkedList<>(progamerCountMap.entrySet());
                entryList.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));

                int total = progamerEntities.size();
                int size = entryList.size();

                List<NextLckRankingViewResponseItem> items = new ArrayList<>();
                for(int ranking = 1; ranking <= 5; ranking++) {
                    if (ranking <= size) {
                        Entry<ProgamerEntity, Long> progamerEntityLongEntry = entryList.get(ranking-1);
                        ProgamerEntity progamer = progamerEntityLongEntry.getKey();
                        Long count = progamerEntityLongEntry.getValue();

                        items.add(
                            NextLckRankingViewResponseItem.builder()
                                .ranking(ranking)
                                .percent(count.floatValue()/total)
                                .progamer(new NextLckRankingProgamerView(progamer))
                                .build()
                        );
                    } else {
                        items.add(
                            NextLckRankingViewResponseItem.builder()
                                .ranking(ranking)
                                .percent(null)
                                .progamer(null)
                                .build()
                        );
                    }
                }

                results.add(
                    NextLckRankingViewResponse.builder()
                        .team(team)
                        .position(pos)
                        .items(items)
                        .build()
                );
            }
        }

        return results;
    }
}
