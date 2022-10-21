package gg.stove.domain.user.service;

import java.util.List;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import gg.stove.domain.user.dto.LoginRequest;
import gg.stove.domain.user.dto.SignupRequest;
import gg.stove.domain.user.entity.Authority;
import gg.stove.domain.user.entity.UserEntity;
import gg.stove.domain.user.repository.UserRepository;
import gg.stove.utils.JwtTokenProvider;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.BDDAssertions.then;

@Transactional
@SpringBootTest
class UserServiceTest {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Test
    void signup() {
        // given
        SignupRequest signupRequest = SignupRequest.builder()
            .email("email@email.com")
            .password("password")
            .build();

        // when
        userService.signup(signupRequest);

        // then
        List<UserEntity> users = userRepository.findAll();
        then(users.size()).isEqualTo(1);
        then(users.get(0).getEmail()).isEqualTo("email@email.com");
        then(users.get(0).getAuthority()).isEqualTo(Authority.ROLE_USER);
    }

    @Test
    void signupExistsEmail() {
        // given
        SignupRequest signupRequest = SignupRequest.builder()
            .email("email@email.com")
            .password("password")
            .build();

        userRepository.save(new UserEntity("email@email.com", "password", "nickname"));

        // when then
        assertThatThrownBy(() -> userService.signup(signupRequest)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void login() {
        // given
        SignupRequest signupRequest = SignupRequest.builder()
            .email("email@email.com")
            .password("password")
            .build();

        userService.signup(signupRequest);

        LoginRequest loginRequest = LoginRequest.builder()
            .email("email@email.com")
            .password("password")
            .build();

        // when then
        String token = userService.login(loginRequest);
        Long id = userRepository.findAll().get(0).getId();
        then(token).isEqualTo(jwtTokenProvider.generateToken(id));
    }

    @Test
    void loginWrongPassword() {
        // given
        SignupRequest signupRequest = SignupRequest.builder()
            .email("email@email.com")
            .password("password")
            .build();

        userService.signup(signupRequest);

        LoginRequest loginRequest = LoginRequest.builder()
            .email("email@email.com")
            .password("wrong")
            .build();

        // when then
        assertThatThrownBy(() -> userService.login(loginRequest)).isInstanceOf(IllegalArgumentException.class);
    }
}