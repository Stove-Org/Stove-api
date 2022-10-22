package gg.stove.domain.nextlck.dto;

import java.util.List;
import java.util.stream.Collectors;

import gg.stove.domain.progamer.entity.ProgamerEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class NextLckViewResponse {

    private List<NextLckRoasterResponse> roasters;
    private List<NextLckPlayerResponse> reserveProgamers;

    public static NextLckViewResponse of(List<NextLckRoasterResponse> roasters, List<ProgamerEntity> unSelectedPlayers) {
        return NextLckViewResponse.builder()
            .roasters(roasters)
            .reserveProgamers(
                unSelectedPlayers.stream()
                .map(NextLckPlayerResponse::from)
                .collect(Collectors.toList())
            )
            .build();
    }
}
