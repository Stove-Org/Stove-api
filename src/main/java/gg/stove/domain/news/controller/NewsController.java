package gg.stove.domain.news.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import gg.stove.annotation.AdminCheck;
import gg.stove.domain.news.dto.CreateNewsRequest;
import gg.stove.domain.news.service.NewsService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class NewsController {
    private final NewsService newsService;

    @AdminCheck
    @PostMapping(value = "/api/v1/news")
    @ResponseStatus(HttpStatus.CREATED)
    public void createNews(@RequestBody @Valid CreateNewsRequest request) {
        newsService.createNews(request);
    }
}
