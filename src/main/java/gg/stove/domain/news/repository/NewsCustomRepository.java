package gg.stove.domain.news.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import gg.stove.domain.news.dto.AdminNewsViewResponse;
import gg.stove.domain.news.dto.NewsViewResponse;

public interface NewsCustomRepository {

    Page<NewsViewResponse> getPublishedNews(Pageable pageable);

    Page<AdminNewsViewResponse> getAllNews(Pageable pageable);
}
