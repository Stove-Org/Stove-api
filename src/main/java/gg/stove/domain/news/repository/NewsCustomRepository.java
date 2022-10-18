package gg.stove.domain.news.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import gg.stove.domain.news.dto.NewsViewResponse;

public interface NewsCustomRepository {

    Page<NewsViewResponse> getNewsPage(Pageable pageable);

}
