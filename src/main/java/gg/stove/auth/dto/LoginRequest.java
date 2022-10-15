package gg.stove.auth.dto;

import javax.validation.constraints.NotBlank;

import lombok.Getter;

@Getter
public class LoginRequest {

    @NotBlank
    private String email;

    @NotBlank
    private String password;
}
