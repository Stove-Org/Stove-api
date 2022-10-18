package gg.stove.domain.news.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;
import gg.stove.domain.news.dto.CreateNewsRequest;
import gg.stove.domain.news.entity.NewsEntity;
import gg.stove.domain.news.repository.NewsRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class NewsService {

    private final NewsRepository newsRepository;

    @Transactional
    public void createNews(CreateNewsRequest request) {
        NewsEntity newsEntity = request.toNewsEntity();
        newsRepository.save(newsEntity);
    }
}
