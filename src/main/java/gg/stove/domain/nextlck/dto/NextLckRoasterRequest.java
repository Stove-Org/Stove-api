package gg.stove.domain.nextlck.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NextLckRoasterRequest {

    private Long teamId;
    private Long topId;
    private Long jglId;
    private Long midId;
    private Long botId;
    private Long sptId;
}
