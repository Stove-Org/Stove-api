package gg.stove.domain.news.controller;

import java.util.List;
import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import gg.stove.annotation.AdminCheck;
import gg.stove.domain.news.dto.CreateNewsRequest;
import gg.stove.domain.news.dto.HotNewsViewResponse;
import gg.stove.domain.news.dto.NewsViewResponse;
import gg.stove.domain.news.dto.UpdatedNewsRequest;
import gg.stove.domain.news.service.NewsService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class NewsController {
    private final NewsService newsService;

    @GetMapping("/api/v1/news")
    public Page<NewsViewResponse> getNews(
        @RequestParam(value = "offset", defaultValue = "0") Integer offset,
        @RequestParam(value = "limit", defaultValue = "10") Integer limit
    ) {
        Pageable pageable = PageRequest.of(offset, limit);
        return newsService.getNewsPage(pageable);
    }

    @GetMapping("/api/v1/news/hot")
    public List<HotNewsViewResponse> getHotNews() {
        return newsService.getHotNews();
    }

    @AdminCheck
    @PostMapping(value = "/api/v1/news")
    @ResponseStatus(HttpStatus.CREATED)
    public void createNews(@RequestBody @Valid CreateNewsRequest request) {
        newsService.createNews(request);
    }

    @AdminCheck
    @PutMapping("/api/v1/news/{newsId}")
    public void updateNews(@PathVariable Long newsId, @RequestBody @Valid UpdatedNewsRequest request) {
        newsService.updateNews(newsId, request);
    }

    @AdminCheck
    @DeleteMapping("/api/v1/news/{newsId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteNews(@PathVariable Long newsId) {
        newsService.deleteNews(newsId);
    }

    @PostMapping("/api/v1/news/{newsId}/view")
    public void increaseViewCount(@PathVariable Long newsId) {
        newsService.increaseViewCount(newsId);
    }
}
