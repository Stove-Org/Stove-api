package gg.stove.domain.nextlck.dto;

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
}
