package gg.stove.domain.news.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.querydsl.core.annotations.QueryProjection;
import gg.stove.domain.news.entity.NewsEntity;
import gg.stove.utils.DateUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NewsViewResponse implements Serializable {

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

    public NewsViewResponse(NewsEntity entity) {
        this.id = entity.getId();
        this.headline = entity.getHeadline();
        this.linkUrl = entity.getLinkUrl();
        this.imageUrl = entity.getImageUrl();
        this.publishedAt = DateUtil.convertToWithWeekString(entity.getPublishedAt());
        this.viewsCount = entity.getViewCount();
    }
}
