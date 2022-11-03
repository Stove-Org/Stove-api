package gg.stove.domain.news.service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import gg.stove.cache.annotation.RedisCacheEvict;
import gg.stove.cache.annotation.RedisCacheEvicts;
import gg.stove.cache.annotation.RedisCacheable;
import gg.stove.domain.hashtags.entity.HashtagEntity;
import gg.stove.domain.hashtags.repository.HashtagRepository;
import gg.stove.domain.news.dto.AdminNewsViewResponse;
import gg.stove.domain.news.dto.CreateNewsRequest;
import gg.stove.domain.news.dto.HotNewsViewResponse;
import gg.stove.domain.news.dto.NaverSearchNewsResponse;
import gg.stove.domain.news.dto.NaverSearchNewsResponseItem;
import gg.stove.domain.news.dto.NewsViewResponse;
import gg.stove.domain.news.dto.UpdatedNewsRequest;
import gg.stove.domain.news.entity.NewsEntity;
import gg.stove.domain.news.entity.NewsHashtagEntity;
import gg.stove.domain.news.repository.NewsHashtagRepository;
import gg.stove.domain.news.repository.NewsRepository;
import gg.stove.exception.DataNotFoundException;
import gg.stove.utils.DateUtil;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class NewsService {

    private final NewsRepository newsRepository;
    private final HashtagRepository hashtagRepository;
    private final NewsHashtagRepository newsHashtagRepository;
    private final NaverSearchService naverSearchService;

    @Transactional
    @RedisCacheEvicts(evicts = {
        @RedisCacheEvict(key = "NewsService.getPublishedNews"),
        @RedisCacheEvict(key = "NewsService.getAllNews"),
        @RedisCacheEvict(key = "NewsService.getHotNews"),
    })
    public void createNews(CreateNewsRequest request) {
        NewsEntity newsEntity = request.toNewsEntity();
        newsRepository.save(newsEntity);

        Set<String> hashtags = request.getHashtags();
        Set<HashtagEntity> hashtagEntities = new HashSet<>(hashtagRepository.findAllByHashtagIn(hashtags));

        hashtagEntities.stream().map(HashtagEntity::getHashtag).forEach(hashtags::remove);

        List<HashtagEntity> createHashtags = hashtags.stream()
            .map(HashtagEntity::new)
            .collect(Collectors.toList());

        List<HashtagEntity> createHashtagEntities = hashtagRepository.saveAll(createHashtags);
        List<NewsHashtagEntity> newsHashtagEntities = createHashtagEntities.stream()
            .map(createHashtagEntity -> NewsHashtagEntity.builder()
                .newsEntity(newsEntity)
                .hashtag(createHashtagEntity)
                .build()
            ).collect(Collectors.toList());

        hashtagEntities.stream()
            .map(hashtagEntity -> NewsHashtagEntity.builder()
                .newsEntity(newsEntity)
                .hashtag(hashtagEntity)
                .build()
            ).forEach(newsHashtagEntities::add);

        newsHashtagRepository.saveAll(newsHashtagEntities);
    }

    @RedisCacheable(key = "NewsService.getPublishedNews", expireSecond = 1800L)
    public Page<NewsViewResponse> getPublishedNews(Pageable pageable) {
        return newsRepository.getPublishedNews(pageable);
    }

    @RedisCacheable(key = "NewsService.getAllNews", expireSecond = 1800L)
    public Page<AdminNewsViewResponse> getAllNews(Pageable pageable) {
        return newsRepository.getAllNews(pageable);
    }

    @Transactional
    @RedisCacheEvicts(evicts = {
        @RedisCacheEvict(key = "NewsService.getPublishedNews"),
        @RedisCacheEvict(key = "NewsService.getAllNews"),
        @RedisCacheEvict(key = "NewsService.getHotNews"),
    })
    public void updateNews(Long newsId, UpdatedNewsRequest request) {
        NewsEntity newsEntity = newsRepository.findById(newsId).orElseThrow(
            () -> new DataNotFoundException("newsId에 해당하는 데이터가 존재하지 않습니다.")
        );
        newsEntity.update(request);

        Set<String> hashtags = request.getHashtags();
        Set<HashtagEntity> hashtagEntities = newsHashtagRepository.findAllByNewsEntity(newsEntity).stream()
            .map(NewsHashtagEntity::getHashtag).collect(Collectors.toSet());

        List<HashtagEntity> newHashtags = hashtags.stream()
            .map(h -> HashtagEntity.builder().hashtag(h).build())
            .filter(hashtagEntity -> !hashtagEntities.contains(hashtagEntity))
            .collect(Collectors.toList());

        List<HashtagEntity> existsHashtags = hashtagRepository.findAllByHashtagIn(hashtags);
        newHashtags.removeAll(existsHashtags);
        List<HashtagEntity> createHashtags = hashtagRepository.saveAll(newHashtags);

        createHashtags.addAll(existsHashtags);
        List<NewsHashtagEntity> newsHashtagEntities = createHashtags.stream()
            .map(createHashtagEntity -> NewsHashtagEntity.builder()
                .newsEntity(newsEntity)
                .hashtag(createHashtagEntity)
                .build()
            ).collect(Collectors.toList());
        newsHashtagRepository.saveAll(newsHashtagEntities);

        List<HashtagEntity> excludeHashtags = hashtagEntities.stream()
            .filter(hashtagEntity -> !hashtags.contains(hashtagEntity.getHashtag()))
            .collect(Collectors.toList());

        excludeHashtags.forEach(excludeHashtag -> newsHashtagRepository.deleteAllByNewsEntityAndHashtag(newsEntity, excludeHashtag));

        List<HashtagEntity> removeHashtags = excludeHashtags.stream()
            .filter(excludeHashtag -> !newsHashtagRepository.existsByHashtag(excludeHashtag))
            .collect(Collectors.toList());

        hashtagRepository.deleteAll(removeHashtags);
    }

    @Transactional
    @RedisCacheEvicts(evicts = {
        @RedisCacheEvict(key = "NewsService.getPublishedNews"),
        @RedisCacheEvict(key = "NewsService.getAllNews"),
        @RedisCacheEvict(key = "NewsService.getHotNews"),
    })
    public void deleteNews(Long newsId) {
        NewsEntity newsEntity = newsRepository.findById(newsId).orElseThrow(() -> new DataNotFoundException("newsId에 해당하는 데이터가 존재하지 않습니다."));

        Set<HashtagEntity> hashtagEntities = newsHashtagRepository.findAllByNewsEntity(newsEntity).stream()
            .map(NewsHashtagEntity::getHashtag).collect(Collectors.toSet());
        newsHashtagRepository.deleteAllByNewsEntity(newsEntity);

        List<HashtagEntity> removeHashtags = hashtagEntities.stream()
            .filter(h -> !newsHashtagRepository.existsByHashtag(h))
            .collect(Collectors.toList());
        hashtagRepository.deleteAll(removeHashtags);
        newsRepository.delete(newsEntity);
    }

    @Transactional
    public void increaseViewCount(Long newsId) {
        NewsEntity newsEntity = newsRepository.findById(newsId).orElseThrow(() -> new DataNotFoundException("newsId에 해당하는 데이터가 존재하지 않습니다."));
        newsEntity.increaseViewCount();
    }

    @RedisCacheable(key = "NewsService.getHotNews", expireSecond = 1800L)
    public List<HotNewsViewResponse> getHotNews() {
        List<NewsEntity> newsEntities = newsRepository.findAll();

        Map<NewsEntity, Long> newHotScoreMap = new HashMap<>();
        for (NewsEntity newsEntity : newsEntities) {
            long untilDay = DateUtil.getUntilDayFromToday(newsEntity.getUploadedAt());
            double dayPow = Math.pow(untilDay, untilDay) * 100;
            Long viewCount = newsEntity.getViewCount();
            newHotScoreMap.put(newsEntity, (long) (viewCount-dayPow));
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

    /**
     * Lck 관련 뉴스 100개를 긁어와, 일주일 이내 기사들 중 존재하지 않는 link들을 가져온다.
     */
    public void syncNaverNews() {
        NaverSearchNewsResponse news = naverSearchService.getNews("lck", 100, 1, "date");
        List<NaverSearchNewsResponseItem> items = news.getItems();
        LocalDateTime weekAgo = LocalDateTime.now().minusDays(7);

        Set<String> set = newsRepository.findAllByUpdatedAtAfter(weekAgo).stream()
            .map(NewsEntity::getLinkUrl)
            .collect(Collectors.toSet());

        List<NewsEntity> newsEntities = items.stream()
            .map(NaverSearchNewsResponseItem::toNewsEntity)
            .takeWhile(newsEntity -> !weekAgo.isAfter(newsEntity.getUploadedAt()))
            .filter(newsEntity -> !set.contains(newsEntity.getLinkUrl()))
            .collect(Collectors.toList());

        newsRepository.saveAll(newsEntities);
    }
}
