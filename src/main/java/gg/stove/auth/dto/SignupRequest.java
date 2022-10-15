package gg.stove.auth.dto;

import javax.validation.constraints.NotBlank;

import lombok.Getter;

@Getter
public class SignupRequest {

    @NotBlank
    private String email;

    @NotBlank
    private String password;
}
