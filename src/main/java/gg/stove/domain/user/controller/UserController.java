package gg.stove.domain.user.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import gg.stove.auth.annotation.LoginCheck;
import gg.stove.domain.user.dto.LoginRequest;
import gg.stove.domain.user.dto.SignupRequest;
import gg.stove.domain.user.dto.UserInfoView;
import gg.stove.domain.user.model.AuthUser;
import gg.stove.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @LoginCheck
    @GetMapping("/api/v1/users")
    public UserInfoView getUserInfo(@AuthenticationPrincipal AuthUser user) {
        return UserInfoView.from(user);
    }

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
