package gg.stove.domain.news.service;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import gg.stove.domain.news.dto.CreateNewsRequest;
import gg.stove.domain.news.dto.HotNewsViewResponse;
import gg.stove.domain.news.dto.NewsViewResponse;
import gg.stove.domain.news.dto.UpdatedNewsRequest;
import gg.stove.domain.news.entity.NewsEntity;
import gg.stove.domain.news.repository.NewsRepository;
import gg.stove.exception.DataNotFoundException;
import gg.stove.utils.DateUtil;
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

    public List<HotNewsViewResponse> getHotNews() {
        List<NewsEntity> newsEntities = newsRepository.findAll();

        Map<NewsEntity, Long> newHotScoreMap = new HashMap<>();
        for (NewsEntity newsEntity : newsEntities) {
            long untilDay = DateUtil.getUntilDayFromToday(newsEntity.getPublishedAt());
            double dayPow = Math.pow(untilDay, untilDay) * 100;
            Long viewCount = newsEntity.getViewCount();
            Long hotNewsWeight = newsEntity.getHotNewsWeight();
            newHotScoreMap.put(newsEntity, (long) (hotNewsWeight+viewCount-dayPow));
        }

        List<Map.Entry<NewsEntity, Long>> entryList = new LinkedList<>(newHotScoreMap.entrySet());
        entryList.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));

        if (entryList.size() > 10) {
            entryList = entryList.subList(0, 10);
        }

        return entryList.stream()
            .map(entry -> new HotNewsViewResponse(entry.getKey(), entry.getValue()))
            .collect(Collectors.toList());
    }
}
