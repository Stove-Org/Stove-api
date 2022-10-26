package gg.stove.domain.nextlck.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import gg.stove.domain.nextlck.entity.NextLckEntity;
import gg.stove.domain.nextlck.repository.NextLckRepository;
import gg.stove.domain.progamer.entity.Position;
import gg.stove.domain.progamer.entity.ProgamerEntity;
import gg.stove.domain.progamer.factory.ProgamerFactory;
import gg.stove.domain.team.Team;
import gg.stove.domain.user.entity.UserEntity;
import gg.stove.domain.user.factory.UserFactory;

@Component
public class NextLckFactory {
    @Autowired
    private NextLckRepository nextLckRepository;

    @Autowired
    private UserFactory userFactory;

    @Autowired
    private ProgamerFactory progamerFactory;

    public NextLckEntity create() {
        return nextLckRepository.save(
            NextLckEntity.builder()
                .user(userFactory.create())
                .teamId(1)
                .position(Position.TOP)
                .progamer(progamerFactory.create("name1", "nickname1", Position.TOP))
                .build()
        );
    }

    public NextLckEntity create(UserEntity user) {
        return nextLckRepository.save(
            NextLckEntity.builder()
                .user(user)
                .teamId(1)
                .position(Position.TOP)
                .progamer(progamerFactory.create("name1", "nickname1", Position.TOP))
                .build()
        );
    }

    public NextLckEntity create(
        UserEntity user,
        Team team,
        Position position,
        ProgamerEntity progamer
    ) {
        return nextLckRepository.save(
            NextLckEntity.builder()
                .user(user)
                .teamId(team.getId())
                .position(position)
                .progamer(progamer)
                .build()
        );
    }
}
