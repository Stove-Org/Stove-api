package gg.stove.domain.news.dto;

import gg.stove.domain.news.entity.NewsEntity;
import gg.stove.utils.DateUtil;
import lombok.Data;

@Data
public class HotNewsViewResponse {

    private Long id;

    private String headline;

    private String linkUrl;

    private String imageUrl;

    private String publishedAt;

    private Long viewsCount;

    private Long score;

    public HotNewsViewResponse(NewsEntity entity, Long score) {
        this.id = entity.getId();
        this.headline = entity.getHeadline();
        this.linkUrl = entity.getLinkUrl();
        this.imageUrl = entity.getImageUrl();
        this.publishedAt = DateUtil.convertToWithWeekString(entity.getPublishedAt());
        this.viewsCount = entity.getViewCount();
        this.score = score;
    }
}
