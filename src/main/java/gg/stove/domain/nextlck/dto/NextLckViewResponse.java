package gg.stove.domain.nextlck.dto;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class NextLckViewResponse {

    private List<NextLckRoasterResponse> roasters;

    public static NextLckViewResponse of(List<NextLckRoasterResponse> roasters) {
        return NextLckViewResponse.builder()
            .roasters(roasters)
            .build();
    }
}
