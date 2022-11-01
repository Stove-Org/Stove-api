package gg.stove.domain.news.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import gg.stove.config.NaverSearchHeaderConfiguration;
import gg.stove.domain.news.dto.NaverSearchNewsResponse;

@FeignClient(name = "naver-search", url = "${feign.naver-search.url}", configuration = NaverSearchHeaderConfiguration.class)
public interface NaverSearchService {

    @GetMapping(value = "/news.json", produces = "application/json", consumes = "application/json")
    NaverSearchNewsResponse getNews(
        @RequestParam("query") String query,
        @RequestParam("display") Integer display,
        @RequestParam("start") Integer start,
        @RequestParam("sort") String sort
    );
}
