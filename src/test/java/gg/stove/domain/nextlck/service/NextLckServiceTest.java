package gg.stove.domain.nextlck.service;

import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import gg.stove.domain.nextlck.dto.NextLckSaveRequest;
import gg.stove.domain.nextlck.dto.NextLckViewResponse;
import gg.stove.domain.nextlck.entity.NextLckEntity;
import gg.stove.domain.nextlck.factory.NextLckFactory;
import gg.stove.domain.nextlck.repository.NextLckRepository;
import gg.stove.domain.progamer.dto.ProgamerViewResponse;
import gg.stove.domain.progamer.entity.Position;
import gg.stove.domain.progamer.entity.ProgamerEntity;
import gg.stove.domain.progamer.factory.ProgamerFactory;
import gg.stove.domain.team.Team;
import gg.stove.domain.user.entity.UserEntity;
import gg.stove.domain.user.factory.UserFactory;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.fail;
import static org.assertj.core.api.BDDAssertions.then;

@Transactional
@SpringBootTest
class NextLckServiceTest {

    @Autowired
    private NextLckService nextLckService;

    @Autowired
    private UserFactory userFactory;

    @Autowired
    private ProgamerFactory progamerFactory;

    @Autowired
    private NextLckFactory nextLckFactory;

    @Autowired
    private NextLckRepository nextLckRepository;

    @Test
    void loadNextLck() {
        // given
        UserEntity user = userFactory.create();
        ProgamerEntity pro1 = progamerFactory.create();
        nextLckFactory.create(user, Team.T1, Position.TOP, pro1);

        ProgamerEntity pro2 = progamerFactory.create();
        nextLckFactory.create(user, Team.DRX, Position.BOT, pro2);

        // when
        List<NextLckViewResponse> nextLckViewResponses = nextLckService.loadNextLck(user.getId());

        // then
        then(nextLckViewResponses.size()).isEqualTo(2L);
        then(nextLckViewResponses.get(0).getTeam()).isEqualTo(Team.T1);
        then(nextLckViewResponses.get(0).getPosition()).isEqualTo(Position.TOP);
        then(nextLckViewResponses.get(0).getProgamer()).isEqualTo(new ProgamerViewResponse(pro1));

        then(nextLckViewResponses.get(1).getTeam()).isEqualTo(Team.DRX);
        then(nextLckViewResponses.get(1).getPosition()).isEqualTo(Position.BOT);
        then(nextLckViewResponses.get(1).getProgamer()).isEqualTo(new ProgamerViewResponse(pro2));
    }

    @Test
    void saveNextLck() {
        // given
        UserEntity user = userFactory.create();

        List<NextLckSaveRequest> nextLckSaveRequest = new ArrayList<>();

        ProgamerEntity pro = progamerFactory.create();
        nextLckSaveRequest.add(new NextLckSaveRequest(Team.T1, Position.TOP, pro.getId()));
        nextLckSaveRequest.add(new NextLckSaveRequest(Team.T1, Position.TOP, pro.getId())); // X
        nextLckSaveRequest.add(new NextLckSaveRequest(Team.GEN, Position.MID, pro.getId()));

        // when
        nextLckService.saveNextLck(user.getId(), nextLckSaveRequest);

        // then
        List<NextLckEntity> nextLckEntities = nextLckRepository.findAll();
        then(nextLckEntities.size()).isEqualTo(2L);

        for (NextLckEntity nextLckEntity : nextLckEntities) {
          if (nextLckEntity.getTeamId().equals(Team.T1.getId())) {
              then(nextLckEntity.getTeamId()).isEqualTo(Team.T1.getId());
              then(nextLckEntity.getPosition()).isEqualTo(Position.TOP);
              then(nextLckEntity.getProgamer()).isEqualTo(pro);
          } else if (nextLckEntity.getTeamId().equals(Team.GEN.getId())) {
              then(nextLckEntity.getTeamId()).isEqualTo(Team.GEN.getId());
              then(nextLckEntity.getPosition()).isEqualTo(Position.MID);
              then(nextLckEntity.getProgamer()).isEqualTo(pro);
          } else {
              fail("fail");
          }
        }
    }

    @Test
    void getParticipantsCount() {
        // given
        nextLckFactory.create();
        nextLckFactory.create();

        UserEntity user = userFactory.create();
        nextLckFactory.create(user);
        nextLckFactory.create(user);

        // when then
        then(nextLckRepository.count()).isEqualTo(4L);
        then(nextLckService.getParticipantsCount().getCount()).isEqualTo(3L);
    }
}