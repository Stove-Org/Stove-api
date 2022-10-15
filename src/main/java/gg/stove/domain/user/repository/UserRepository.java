package gg.stove.domain.user.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import gg.stove.domain.user.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

}
