package gg.stove.domain.progamer.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import gg.stove.domain.progamer.entity.ProgamerEntity;

public interface ProgamerRepository extends JpaRepository<ProgamerEntity, Long>, ProgamerCustomRepository {
}
