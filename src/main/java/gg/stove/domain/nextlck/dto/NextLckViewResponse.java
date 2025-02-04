package gg.stove.domain.nextlck.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import gg.stove.domain.nextlck.entity.NextLckEntity;
import gg.stove.domain.progamer.dto.ProgamerViewResponse;
import gg.stove.domain.progamer.entity.Position;
import gg.stove.domain.team.Team;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
public class NextLckViewResponse {

    private Team team;
    private Position position;
    private ProgamerViewResponse progamer;

    public NextLckViewResponse(Team team, Position position, ProgamerViewResponse progamerId) {
        this.team = team;
        this.position = position;
        this.progamer = progamerId;
    }

    public static NextLckViewResponse of(NextLckEntity nextLckEntity) {
        return new NextLckViewResponse(
            Team.of(nextLckEntity.getTeamId()),
            nextLckEntity.getPosition(),
            new ProgamerViewResponse(nextLckEntity.getProgamer())
        );
    }

    public static List<NextLckViewResponse> of(List<NextLckEntity> rosters) {
        Map<TeamAndPositionKey, NextLckEntity> dict = rosters.stream()
            .collect(Collectors.toMap(nextLck -> new TeamAndPositionKey(Team.of(nextLck.getTeamId()), nextLck.getPosition()), nextLck -> nextLck));

        List<Team> teams = List.of(Team.DRX, Team.T1, Team.GEN, Team.DK, Team.LSB, Team.KT, Team.KDF, Team.NS, Team.BRO, Team.HLE);
        List<Position> positions = List.of(Position.TOP, Position.JGL, Position.MID, Position.BOT, Position.SPT);

        List<NextLckViewResponse> results = new ArrayList<>();
        for (Team team : teams) {
            for (Position pos : positions) {
                TeamAndPositionKey key = new TeamAndPositionKey(team, pos);

                if (dict.containsKey(key)) {
                    results.add(NextLckViewResponse.of(dict.get(key)));
                } else {
                    results.add(new NextLckViewResponse(team, pos, null));
                }
            }
        }

        return results;
    }
}
