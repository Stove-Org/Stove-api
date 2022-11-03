package gg.stove.domain.news.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import gg.stove.domain.hashtags.entity.HashtagEntity;
import gg.stove.domain.news.entity.NewsEntity;
import gg.stove.domain.news.entity.NewsHashtagEntity;

public interface NewsHashtagRepository extends JpaRepository<NewsHashtagEntity, Long> {

    List<NewsHashtagEntity> findAllByNewsEntity(NewsEntity newsEntity);

    void deleteAllByNewsEntityAndHashtag(NewsEntity newsEntity, HashtagEntity hashtagEntity);
    void deleteAllByNewsEntity(NewsEntity newsEntity);

    boolean existsByHashtag(HashtagEntity hashtagEntity);
}
