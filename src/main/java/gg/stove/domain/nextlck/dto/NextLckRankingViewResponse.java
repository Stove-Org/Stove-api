package gg.stove.domain.nextlck.dto;

import java.io.Serializable;
import java.util.List;

import gg.stove.domain.progamer.entity.Position;
import gg.stove.domain.team.Team;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NextLckRankingViewResponse implements Serializable {

    private Team team;
    private Position position;
    private List<NextLckRankingViewResponseItem> items;
}
