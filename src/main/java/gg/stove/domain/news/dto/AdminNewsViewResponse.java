package gg.stove.domain.news.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import com.querydsl.core.annotations.QueryProjection;
import gg.stove.domain.hashtags.dto.HashtagView;
import gg.stove.domain.news.entity.NewsEntity;
import gg.stove.utils.DateUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AdminNewsViewResponse implements Serializable {

    private Long id;

    private String headline;

    private String linkUrl;

    private String imgUrl;

    private String uploadedAt;

    private Long viewsCount;

    private Boolean isPublished;
    private List<HashtagView> hashtagViews;

    @QueryProjection
    public AdminNewsViewResponse(Long id, String headline, String linkUrl, String imgUrl, LocalDateTime uploadedAt, Long viewsCount, Boolean isPublished, List<HashtagView> hashtagViews) {
        this.id = id;
        this.headline = headline;
        this.linkUrl = linkUrl;
        this.imgUrl = imgUrl;
        this.uploadedAt = DateUtil.convertToWithWeekString(uploadedAt);
        this.viewsCount = viewsCount;
        this.isPublished = isPublished;
        this.hashtagViews = hashtagViews;
    }

    public AdminNewsViewResponse(NewsEntity entity) {
        this.id = entity.getId();
        this.headline = entity.getHeadline();
        this.linkUrl = entity.getLinkUrl();
        this.imgUrl = entity.getImgUrl();
        this.uploadedAt = DateUtil.convertToWithWeekString(entity.getUploadedAt());
        this.viewsCount = entity.getViewCount();
        this.isPublished = entity.getIsPublished();
    }
}
