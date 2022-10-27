package gg.stove.domain.user.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import gg.stove.domain.user.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByNickname(String nickname);
}
