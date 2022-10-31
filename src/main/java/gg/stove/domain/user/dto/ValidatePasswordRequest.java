package gg.stove.domain.user.dto;

import javax.validation.constraints.NotBlank;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ValidatePasswordRequest {
    @NotBlank
    private String password;
}
