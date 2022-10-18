package gg.stove.domain.news.dto;

import java.time.LocalDateTime;

import com.querydsl.core.annotations.QueryProjection;
import gg.stove.utils.DateUtil;
import lombok.Data;

@Data
public class NewsViewResponse {

    private Long id;

    private String headline;

    private String linkUrl;

    private String imageUrl;

    private String publishedAt;

    private Long viewsCount;

    @QueryProjection
    public NewsViewResponse(Long id, String headline, String linkUrl, String imageUrl, LocalDateTime publishedAt, Long viewsCount) {
        this.id = id;
        this.headline = headline;
        this.linkUrl = linkUrl;
        this.imageUrl = imageUrl;
        this.publishedAt = DateUtil.convertToWithWeekString(publishedAt);
        this.viewsCount = viewsCount;
    }
}
