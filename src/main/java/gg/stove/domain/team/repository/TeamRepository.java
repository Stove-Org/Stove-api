package gg.stove.domain.team.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import gg.stove.domain.team.entity.TeamEntity;

public interface TeamRepository extends JpaRepository<TeamEntity, Long> {
}
