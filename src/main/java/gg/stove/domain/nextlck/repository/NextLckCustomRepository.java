package gg.stove.domain.nextlck.repository;

import java.util.List;

import gg.stove.domain.nextlck.entity.NextLckEntity;
import gg.stove.domain.user.entity.UserEntity;

public interface NextLckCustomRepository {

    List<NextLckEntity> findAllByUserWithTeamAndPlayers(UserEntity user);
    List<NextLckEntity> findAllByUserWithTeam(UserEntity user);

}
