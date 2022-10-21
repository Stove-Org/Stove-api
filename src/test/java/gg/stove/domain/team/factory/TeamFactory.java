package gg.stove.domain.team.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import gg.stove.domain.team.entity.TeamEntity;
import gg.stove.domain.team.repository.TeamRepository;

@Component
public class TeamFactory {
    @Autowired
    private TeamRepository teamRepository;

    public TeamEntity create() {
        return teamRepository.save(
            TeamEntity.builder()
                .name("T1")
                .build()
        );
    }

    public TeamEntity create(String name) {
        return teamRepository.save(
            TeamEntity.builder()
                .name(name)
                .build()
        );
    }
}
