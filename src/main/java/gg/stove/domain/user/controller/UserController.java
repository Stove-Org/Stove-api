package gg.stove.domain.user.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import gg.stove.domain.user.dto.LoginRequest;
import gg.stove.domain.user.dto.SignupRequest;
import gg.stove.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/api/v1/users/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public void signup(@RequestBody @Valid SignupRequest signupRequest) {
        userService.signup(signupRequest);
    }

    @PostMapping("/api/v1/users/login")
    public String login(@RequestBody @Valid LoginRequest loginRequest) {
        return userService.login(loginRequest);
    }

}
