package gg.stove.domain.user.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import gg.stove.domain.user.entity.UserEntity;
import gg.stove.domain.user.repository.UserRepository;

@Component
public class UserFactory {

    @Autowired
    private UserRepository userRepository;

    public UserEntity create() {
        return userRepository.save(new UserEntity("email@email.com", "password", "nickname"));
    }

    public UserEntity create(String email) {
        return userRepository.save(new UserEntity(email, "password", "nickname"));
    }

    public UserEntity create(String email, String password) {
        return userRepository.save(new UserEntity(email, password, "nickname"));
    }
}
