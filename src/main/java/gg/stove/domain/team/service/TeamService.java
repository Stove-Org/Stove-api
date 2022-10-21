package gg.stove.domain.team.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import gg.stove.cache.annotation.RedisCacheable;
import gg.stove.domain.team.entity.TeamEntity;
import gg.stove.domain.team.repository.TeamRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TeamService {
    private final TeamRepository teamRepository;

    @RedisCacheable(key = "TeamService.getTeamEntities", expireSecond = 3600L)
    public List<TeamEntity> getTeamEntities() {
        return teamRepository.findAll();
    }

    @RedisCacheable(key = "TeamService.getTeamEntityMap", expireSecond = 3600L)
    public Map<Long, TeamEntity> getTeamEntityMap() {
        return getTeamEntities().stream()
            .collect(Collectors.toMap(TeamEntity::getId, progamer -> progamer));
    }
}
