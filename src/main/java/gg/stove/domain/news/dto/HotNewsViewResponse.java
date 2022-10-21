package gg.stove.domain.news.dto;

import java.io.Serializable;

import gg.stove.domain.news.entity.NewsEntity;
import gg.stove.utils.DateUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class HotNewsViewResponse implements Serializable {

    private Long id;

    private String headline;

    private String linkUrl;

    private String imgUrl;

    private String publishedAt;

    private Long viewsCount;

    private Long score;

    public HotNewsViewResponse(NewsEntity entity, Long score) {
        this.id = entity.getId();
        this.headline = entity.getHeadline();
        this.linkUrl = entity.getLinkUrl();
        this.imgUrl = entity.getImgUrl();
        this.publishedAt = DateUtil.convertToWithWeekString(entity.getUploadedAt());
        this.viewsCount = entity.getViewCount();
        this.score = score;
    }
}
