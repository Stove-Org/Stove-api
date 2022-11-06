package gg.stove.domain.nextlck.dto;

import gg.stove.domain.progamer.entity.Position;
import gg.stove.domain.team.Team;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class TeamAndPositionKey {

    private Team team;
    private Position position;

    public TeamAndPositionKey(Team team, Position position) {
        this.team = team;
        this.position = position;
    }
}
