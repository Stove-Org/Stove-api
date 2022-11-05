package gg.stove.domain.news.dto;

import java.io.Serializable;
import java.util.List;

import gg.stove.domain.hashtags.dto.HashtagView;
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

    private String uploadedAt;

    private Long viewsCount;

    private Long score;

    private List<HashtagView> hashtagViews;

    public HotNewsViewResponse(NewsEntity entity, Long score, List<HashtagView> hashtagViews) {
        this.id = entity.getId();
        this.headline = entity.getHeadline();
        this.linkUrl = entity.getLinkUrl();
        this.imgUrl = entity.getImgUrl();
        this.uploadedAt = DateUtil.convertToWithWeekString(entity.getUploadedAt());
        this.viewsCount = entity.getViewCount();
        this.score = score;
        this.hashtagViews = hashtagViews;
    }
}
