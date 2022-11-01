package gg.stove.domain.news.dto;

import gg.stove.domain.news.entity.NewsEntity;
import gg.stove.utils.DateUtil;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class NaverSearchNewsResponseItem {
    private String title;
    private String originallink;
    private String link;
    private String description;
    private String pubDate;

    public NewsEntity toNewsEntity() {
        return NewsEntity.builder()
            .headline(title)
            .linkUrl(originallink)
            .isPublished(false)
            .uploadedAt(DateUtil.convertByNaverNewsDate(pubDate))
            .build();
    }
}
