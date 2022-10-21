package gg.stove.domain.user.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import gg.stove.domain.user.entity.UserEntity;
import gg.stove.domain.user.repository.UserRepository;
import gg.stove.utils.JwtTokenProvider;

@Component
public class UserFactory {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    public UserEntity create() {
        return userRepository.save(new UserEntity("email@email.com", bCryptPasswordEncoder.encode("password"), "nickname"));
    }

    public UserEntity create(String email) {
        return userRepository.save(new UserEntity(email, bCryptPasswordEncoder.encode("password"), "nickname"));
    }

    public UserEntity create(String email, String password) {
        return userRepository.save(new UserEntity(email, bCryptPasswordEncoder.encode(password), "nickname"));
    }
}
