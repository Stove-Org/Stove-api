package gg.stove.domain.news.repository;


import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import gg.stove.domain.news.entity.NewsEntity;

public interface NewsRepository extends JpaRepository<NewsEntity, Long>, NewsCustomRepository {

    List<NewsEntity> findAllByUpdatedAtAfter(LocalDateTime localDateTime);
    List<NewsEntity> findAllByIsPublishedTrue();
}
