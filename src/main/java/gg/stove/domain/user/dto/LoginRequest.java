package gg.stove.domain.user.dto;

import javax.validation.constraints.NotBlank;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginRequest {

    @NotBlank
    private String email;

    @NotBlank
    private String password;
}
