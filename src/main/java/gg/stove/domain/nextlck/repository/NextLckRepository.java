package gg.stove.domain.nextlck.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import gg.stove.domain.nextlck.entity.NextLckEntity;
import gg.stove.domain.user.entity.UserEntity;

public interface NextLckRepository extends JpaRepository<NextLckEntity, Long>, NextLckCustomRepository {

    List<NextLckEntity> findAllByUser(UserEntity user);

}
