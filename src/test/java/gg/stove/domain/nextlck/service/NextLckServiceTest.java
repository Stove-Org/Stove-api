package gg.stove.domain.nextlck.service;

import java.util.List;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import gg.stove.domain.nextlck.dto.NextLckPlayerResponse;
import gg.stove.domain.nextlck.dto.NextLckRoasterRequest;
import gg.stove.domain.nextlck.dto.NextLckRoasterResponse;
import gg.stove.domain.nextlck.dto.NextLckSaveRequest;
import gg.stove.domain.nextlck.dto.NextLckTeam;
import gg.stove.domain.nextlck.dto.NextLckViewResponse;
import gg.stove.domain.nextlck.entity.NextLckEntity;
import gg.stove.domain.nextlck.factory.NextLckFactory;
import gg.stove.domain.nextlck.repository.NextLckRepository;
import gg.stove.domain.progamer.entity.Position;
import gg.stove.domain.progamer.entity.ProgamerEntity;
import gg.stove.domain.progamer.factory.ProgamerFactory;
import gg.stove.domain.team.entity.TeamEntity;
import gg.stove.domain.team.factory.TeamFactory;
import gg.stove.domain.user.entity.UserEntity;
import gg.stove.domain.user.factory.UserFactory;
import org.junit.jupiter.api.Test;

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
    private TeamFactory teamFactory;

    @Autowired
    private NextLckFactory nextLckFactory;

    @Autowired
    private NextLckRepository nextLckRepository;

    @Test
    void loadNextLck() {
        // given
        UserEntity user = userFactory.create();
        TeamEntity team1 = teamFactory.create("T1");
        ProgamerEntity top1 = progamerFactory.create("top1-name", "top1-nickname", Position.TOP);
        ProgamerEntity jgl1 = progamerFactory.create("jgl1-name", "jgl1-nickname", Position.JGL);
        ProgamerEntity mid1 = progamerFactory.create("mid1-name", "mid1-nickname", Position.MID);
        ProgamerEntity bot1 = progamerFactory.create("bot1-name", "bot1-nickname", Position.BOT);
        ProgamerEntity spt1 = progamerFactory.create("spt1-name", "spt1-nickname", Position.SPT);
        nextLckFactory.create(user, team1, top1, jgl1, mid1, bot1, spt1);

        TeamEntity team2 = teamFactory.create("DRX");
        ProgamerEntity top2 = progamerFactory.create("top2-name", "top2-nickname", Position.TOP);
        ProgamerEntity mid2 = progamerFactory.create("mid2-name", "mid2-nickname", Position.MID);
        ProgamerEntity supporter2 = progamerFactory.create("supporter2-name", "supporter2-nickname", Position.SPT);
        nextLckFactory.create(user, team2, top2, null, mid2, null, supporter2);

        ProgamerEntity unSelectedPlayer1 = progamerFactory.create("un-selected1-name", "un-selected1-nickname", Position.TOP);
        ProgamerEntity unSelectedPlayer2 = progamerFactory.create("un-selected2-name", "un-selected2-nickname", Position.MID);
        ProgamerEntity unSelectedPlayer3 = progamerFactory.create("un-selected3-name", "un-selected3-nickname", Position.JGL);

        // when
        NextLckViewResponse nextLckViewResponse = nextLckService.loadNextLck(user.getId());

        // then
        List<NextLckRoasterResponse> roasters = nextLckViewResponse.getRoasters();
        then(roasters.size()).isEqualTo(2L);
        then(roasters.get(0).getTeam()).isEqualTo(NextLckTeam.of(team1));
        then(roasters.get(0).getTop()).isEqualTo(NextLckPlayerResponse.from(top1));
        then(roasters.get(0).getJgl()).isEqualTo(NextLckPlayerResponse.from(jgl1));
        then(roasters.get(0).getMid()).isEqualTo(NextLckPlayerResponse.from(mid1));
        then(roasters.get(0).getBot()).isEqualTo(NextLckPlayerResponse.from(bot1));
        then(roasters.get(0).getSpt()).isEqualTo(NextLckPlayerResponse.from(spt1));

        then(roasters.get(1).getTeam()).isEqualTo(NextLckTeam.of(team2));
        then(roasters.get(1).getTop()).isEqualTo(NextLckPlayerResponse.from(top2));
        then(roasters.get(1).getJgl()).isNull();
        then(roasters.get(1).getMid()).isEqualTo(NextLckPlayerResponse.from(mid2));
        then(roasters.get(1).getBot()).isNull();
        then(roasters.get(1).getSpt()).isEqualTo(NextLckPlayerResponse.from(supporter2));

        List<NextLckPlayerResponse> unSelectedPlayers = nextLckViewResponse.getReserveProgamers();
        then(unSelectedPlayers.size()).isEqualTo(3L);
        then(unSelectedPlayers).contains(NextLckPlayerResponse.from(unSelectedPlayer1));
        then(unSelectedPlayers).contains(NextLckPlayerResponse.from(unSelectedPlayer2));
        then(unSelectedPlayers).contains(NextLckPlayerResponse.from(unSelectedPlayer3));
    }

    @Test
    void saveNextLck() {
        // given
        UserEntity user = userFactory.create();

        TeamEntity team1 = teamFactory.create("DRX");
        ProgamerEntity top1 = progamerFactory.create("top1-name", "top1-nickname", Position.TOP);
        ProgamerEntity jgl1 = progamerFactory.create("jgl1-name", "jgl1-nickname", Position.JGL);
        ProgamerEntity mid1 = progamerFactory.create("mid1-name", "mid1-nickname", Position.MID);
        ProgamerEntity bot1 = progamerFactory.create("bot1-name", "bot1-nickname", Position.BOT);
        ProgamerEntity spt1 = progamerFactory.create("spt1-name", "spt1-nickname", Position.SPT);
        NextLckEntity nextLckEntity1 = nextLckFactory.create(user, team1, top1, jgl1, mid1, bot1, spt1);

        NextLckRoasterRequest nextLckRoasterRequest1 = NextLckRoasterRequest.builder()
            .teamId(team1.getId())
            .topId(top1.getId())
            .jglId(jgl1.getId())
            .midId(mid1.getId())
            .botId(bot1.getId())
            .sptId(spt1.getId())
            .build();

        TeamEntity team2 = teamFactory.create("T1");
        ProgamerEntity top2 = progamerFactory.create("top2-name", "top2-nickname", Position.TOP);
        ProgamerEntity jgl2 = progamerFactory.create("jgl2-name", "jgl2-nickname", Position.JGL);
        ProgamerEntity mid2 = progamerFactory.create("mid2-name", "mid2-nickname", Position.MID);

        NextLckRoasterRequest nextLckRoasterRequest2 = NextLckRoasterRequest.builder()
            .teamId(team2.getId())
            .topId(top2.getId())
            .jglId(jgl2.getId())
            .midId(mid2.getId())
            .build();

        NextLckSaveRequest nextLckSaveRequest = NextLckSaveRequest.builder()
            .roasters(List.of(nextLckRoasterRequest1, nextLckRoasterRequest2))
            .build();

        // when
        then(nextLckRepository.count()).isEqualTo(1L);
        nextLckService.saveNextLck(user.getId(), nextLckSaveRequest);

        // then
        List<NextLckEntity> nextLckEntities = nextLckRepository.findAll();
        then(nextLckEntities.size()).isEqualTo(2L);
        then(nextLckEntities.get(0)).isEqualTo(nextLckEntity1);

        then(nextLckEntities.get(1).getUser()).isEqualTo(user);
        then(nextLckEntities.get(1).getTeam()).isEqualTo(team2);
        then(nextLckEntities.get(1).getTop()).isEqualTo(top2);
        then(nextLckEntities.get(1).getJgl()).isEqualTo(jgl2);
        then(nextLckEntities.get(1).getMid()).isEqualTo(mid2);
        then(nextLckEntities.get(1).getBot()).isNull();
        then(nextLckEntities.get(1).getSpt()).isNull();
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