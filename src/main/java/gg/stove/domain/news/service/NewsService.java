package gg.stove.domain.news.service;

import java.util.Optional;
import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import gg.stove.domain.news.dto.CreateNewsRequest;
import gg.stove.domain.news.dto.NewsViewResponse;
import gg.stove.domain.news.dto.UpdatedNewsRequest;
import gg.stove.domain.news.entity.NewsEntity;
import gg.stove.domain.news.repository.NewsRepository;
import gg.stove.exception.DataNotFoundException;
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

    public Page<NewsViewResponse> getNewsPage(Pageable pageable) {
        return newsRepository.getNewsPage(pageable);
    }

    @Transactional
    public void updateNews(Long newsId, UpdatedNewsRequest request) {
        NewsEntity newsEntity = newsRepository.findById(newsId).orElseThrow(DataNotFoundException::new);
        newsEntity.update(request);
    }

    public void deleteNews(Long newsId) {
        newsRepository.deleteById(newsId);
    }

    public void increaseViewCount(Long newsId) {
        NewsEntity newsEntity = newsRepository.findById(newsId).orElseThrow(DataNotFoundException::new);
        newsEntity.increaseViewCount();
    }
}
