package gg.stove.domain.user.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ResetNicknameRequest {

    private String nickname;

}