package gg.stove.domain.news.service;

import java.time.LocalDateTime;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import gg.stove.domain.news.dto.CreateNewsRequest;
import gg.stove.domain.news.dto.NewsViewResponse;
import gg.stove.domain.news.dto.UpdatedNewsRequest;
import gg.stove.domain.news.entity.NewsEntity;
import gg.stove.domain.news.factory.NewsFactory;
import gg.stove.domain.news.repository.NewsRepository;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.then;

@Transactional
@SpringBootTest
class NewsServiceTest {

    @Autowired
    private NewsService newsService;

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private NewsFactory newsFactory;

    @Test
    void createNews() {
        // given
        CreateNewsRequest createNewsRequest = CreateNewsRequest.builder()
            .headline("headline")
            .linkUrl("linkUrl")
            .imgUrl("imgUrl")
            .uploadedAt("2022-12-01 11:30")
            .build();

        // when
        newsService.createNews(createNewsRequest);

        // then
        NewsEntity newsEntity = newsRepository.findAll().get(0);
        then(newsEntity.getHeadline()).isEqualTo("headline");
        then(newsEntity.getLinkUrl()).isEqualTo("linkUrl");
        then(newsEntity.getImgUrl()).isEqualTo("imgUrl");
        then(newsEntity.getUploadedAt()).isEqualTo(LocalDateTime.of(2022, 12,1, 11, 30 ));
    }

    @Test
    void getNewsPage() {
        // given
        newsFactory.create();

        // when
        Page<NewsViewResponse> newsPage = newsService.getNewsPage(PageRequest.of(0, 10));

        // then
        then(newsPage.getTotalElements()).isEqualTo(1L);
        NewsViewResponse newsViewResponse = newsPage.getContent().get(0);
        then(newsViewResponse.getHeadline()).isEqualTo("headline");
        then(newsViewResponse.getLinkUrl()).isEqualTo("linkUrl");
        then(newsViewResponse.getImgUrl()).isEqualTo("imgUrl");
        then(newsViewResponse.getPublishedAt()).isEqualTo("2022.12.01 오전 11:30");
        then(newsViewResponse.getViewsCount()).isEqualTo(0);
    }

    @Test
    void updateNews() {
        // given
        NewsEntity newsEntity = newsFactory.create();
        Long id = newsEntity.getId();

        UpdatedNewsRequest updatedNewsRequest = UpdatedNewsRequest.builder()
            .headline("headline1")
            .imgUrl("imgUrl")
            .publishedAt("2021-09-14 17:10")
            .build();

        // when
        newsService.updateNews(id, updatedNewsRequest);

        // then
        NewsEntity updatedNews = newsRepository.findById(id).get();
        then(updatedNews.getHeadline()).isEqualTo("headline1");
        then(updatedNews.getLinkUrl()).isEqualTo("linkUrl");
        then(updatedNews.getImgUrl()).isEqualTo("imgUrl");
        then(updatedNews.getUploadedAt()).isEqualTo(LocalDateTime.of(2021, 9,14, 17, 10 ));
    }

    @Test
    void deleteNews() {
        // given
        NewsEntity newsEntity = newsFactory.create();
        Long id = newsEntity.getId();

        // when
        newsService.deleteNews(id);

        // then
        then(newsRepository.count()).isEqualTo(0);
      }

    @Test
    void increaseViewCount() {
        // given
        NewsEntity newsEntity = newsFactory.create();
        Long id = newsEntity.getId();

        // when, then
        then(newsRepository.findById(id).get().getViewCount()).isEqualTo(0L);
        newsService.increaseViewCount(id);
        then(newsRepository.findById(id).get().getViewCount()).isEqualTo(1L);
      }
}
