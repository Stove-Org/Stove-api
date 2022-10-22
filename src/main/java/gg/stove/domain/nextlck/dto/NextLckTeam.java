package gg.stove.domain.nextlck.dto;

import gg.stove.domain.team.entity.TeamEntity;
import gg.stove.domain.team.entity.TeamName;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@Builder
@EqualsAndHashCode
public class NextLckTeam {
    private Long id;
    private TeamName name;

    public static NextLckTeam of(TeamEntity team) {
        return NextLckTeam.builder()
            .id(team.getId())
            .name(team.getName())
            .build();
    }
}
