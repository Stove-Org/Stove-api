package gg.stove.domain.nextlck.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import gg.stove.domain.nextlck.entity.NextLckEntity;
import gg.stove.domain.nextlck.repository.NextLckRepository;
import gg.stove.domain.progamer.entity.Position;
import gg.stove.domain.progamer.entity.ProgamerEntity;
import gg.stove.domain.progamer.factory.ProgamerFactory;
import gg.stove.domain.team.entity.TeamEntity;
import gg.stove.domain.team.factory.TeamFactory;
import gg.stove.domain.user.entity.UserEntity;
import gg.stove.domain.user.factory.UserFactory;

@Component
public class NextLckFactory {
    @Autowired
    private NextLckRepository nextLckRepository;

    @Autowired
    private UserFactory userFactory;

    @Autowired
    private TeamFactory teamFactory;

    @Autowired
    private ProgamerFactory progamerFactory;

    public NextLckEntity create() {
        return nextLckRepository.save(
            NextLckEntity.builder()
                .user(userFactory.create())
                .team(teamFactory.create())
                .top(progamerFactory.create("name1", "nickname1", Position.TOP))
                .jgl(progamerFactory.create("name2", "nickname2", Position.JGL))
                .mid(progamerFactory.create("name3", "nickname3", Position.MID))
                .bot(progamerFactory.create("name4", "nickname4", Position.BOT))
                .spt(progamerFactory.create("name5", "nickname5", Position.SPT))
                .build()
        );
    }

    public NextLckEntity create(
        UserEntity user,
        TeamEntity team,
        ProgamerEntity top,
        ProgamerEntity jgl,
        ProgamerEntity mid,
        ProgamerEntity bot,
        ProgamerEntity spt
    ) {
        return nextLckRepository.save(
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
