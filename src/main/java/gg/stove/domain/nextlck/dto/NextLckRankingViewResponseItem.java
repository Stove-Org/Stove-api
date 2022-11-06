package gg.stove.domain.nextlck.dto;

import java.io.Serializable;

import gg.stove.domain.progamer.dto.ProgamerViewResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NextLckRankingViewResponseItem implements Serializable {

    private Integer ranking;
    private Float percent;
    private NextLckRankingProgamerView progamer;
}
