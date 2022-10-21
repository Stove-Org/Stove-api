package gg.stove.domain.news.factory;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import gg.stove.domain.news.entity.NewsEntity;
import gg.stove.domain.news.repository.NewsRepository;

@Component
public class NewsFactory {
    @Autowired
    private NewsRepository newsRepository;

    public NewsEntity create() {
        return newsRepository.save(NewsEntity.builder()
            .headline("headline")
            .linkUrl("linkUrl")
            .imageUrl("imageUrl")
            .publishedAt(LocalDateTime.of(2022, 12,1, 11, 30 )) // 2022-12-01 11:30
            .build());
    }
}
