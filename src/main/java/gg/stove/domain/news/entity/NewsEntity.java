package gg.stove.domain.news.entity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import gg.stove.domain.news.dto.UpdatedNewsRequest;
import gg.stove.utils.DateUtil;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Getter
@Table(name = "news")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NewsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "headline")
    private String headline;

    @Column(name = "link_url")
    private String linkUrl;

    @Column(name = "imgUrl")
    private String imgUrl;

    @Column(name = "view_count", columnDefinition = "integer default 0")
    private Long viewCount = 0L;

    @Column(name = "uploaded_at")
    private LocalDateTime uploadedAt;

    @Column(name = "hot_news_weight", columnDefinition = "integer default 0")
    private Long hotNewsWeight = 0L;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Builder
    public NewsEntity(String headline, String linkUrl, String imgUrl, LocalDateTime uploadedAt) {
        this.headline = headline;
        this.linkUrl = linkUrl;
        this.imgUrl = imgUrl;
        this.uploadedAt = uploadedAt;
    }

    public void update(UpdatedNewsRequest request) {
        String headline = request.getHeadline();
        if (headline != null) {
            this.headline = headline;
        }

        String linkUrl = request.getLinkUrl();
        if (linkUrl != null) {
            this.linkUrl = linkUrl;
        }

        String imgUrl = request.getImgUrl();
        if (imgUrl != null) {
            this.imgUrl = imgUrl;
        }

        String publishedAt = request.getPublishedAt();
        if (publishedAt != null) {
            this.uploadedAt = DateUtil.convertWithUntilMinuteString(publishedAt);
        }

        Long hotNewsWeight = request.getHotNewsWeight();
        if (hotNewsWeight != null) {
            this.hotNewsWeight = hotNewsWeight;
        }
    }

    public void increaseViewCount() {
        this.viewCount++;
    }
}
