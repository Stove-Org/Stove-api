package gg.stove.domain.user.service;

import javax.transaction.Transactional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import gg.stove.domain.user.dto.LoginRequest;
import gg.stove.domain.user.dto.ResetNicknameRequest;
import gg.stove.domain.user.dto.ResetPasswordRequest;
import gg.stove.domain.user.dto.SignupRequest;
import gg.stove.domain.user.entity.UserEntity;
import gg.stove.domain.user.repository.UserRepository;
import gg.stove.exception.DataNotFoundException;
import gg.stove.utils.JwtTokenProvider;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public void signup(@NonNull SignupRequest signupRequest) {
        String email = signupRequest.getEmail();
        String nickname = signupRequest.getNickname();
        validateEmail(email);
        validateNickname(nickname);

        String encodePassword = bCryptPasswordEncoder.encode(signupRequest.getPassword());
        UserEntity userEntity = new UserEntity(email, encodePassword, signupRequest.getNickname());
        userRepository.save(userEntity);
    }

    public void validateEmail(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("중복된 이메일입니다.");
        }
    }

    public void validateNickname(String nickname) {
        if (userRepository.existsByNickname(nickname)) {
            throw new IllegalArgumentException("중복된 닉네임입니다.");
        }
    }

    public String login(@NonNull LoginRequest loginRequest) {
        UserEntity userEntity = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(
            () -> new DataNotFoundException("존재하지 않는 이메일입니다.")
        );
        if (!bCryptPasswordEncoder.matches(loginRequest.getPassword(), userEntity.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        return jwtTokenProvider.generateToken(userEntity.getId());
    }

    @Transactional
    public void resetPassword(Long userId, ResetPasswordRequest request) {
        UserEntity userEntity = userRepository.findById(userId).orElseThrow();
        if (!bCryptPasswordEncoder.matches(request.getCurrentPassword(), userEntity.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        String encodePassword = bCryptPasswordEncoder.encode(request.getNewPassword());
        userEntity.updatePassword(encodePassword);
    }

    @Transactional
    public void resetNickname(Long userId, ResetNicknameRequest request) {
        UserEntity userEntity = userRepository.findById(userId).orElseThrow();
        userEntity.updateNickname(request.getNickname());
    }
}
