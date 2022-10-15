package gg.stove.auth.service;

import javax.transaction.Transactional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import gg.stove.utils.JwtTokenProvider;
import gg.stove.auth.dto.LoginRequest;
import gg.stove.auth.dto.SignupRequest;
import gg.stove.domain.user.entity.UserEntity;
import gg.stove.domain.user.repository.UserRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public void signup(@NonNull SignupRequest signupRequest) {
        String email = signupRequest.getEmail();
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException();
        }

        String encodePassword = bCryptPasswordEncoder.encode(signupRequest.getPassword());
        UserEntity userEntity = new UserEntity(email, encodePassword);
        userRepository.save(userEntity);
    }

    public String login(@NonNull LoginRequest loginRequest) {
        UserEntity userEntity = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow();
        if (!bCryptPasswordEncoder.matches(loginRequest.getPassword(), userEntity.getPassword())) {
            throw new IllegalArgumentException();
        }

        return jwtTokenProvider.generateToken(userEntity.getId());
    }
}
