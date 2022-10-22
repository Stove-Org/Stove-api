package gg.stove.domain.nextlck.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NextLckSaveRequest {

    private List<NextLckRoasterRequest> roasters;

}
