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

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "published_at")
    private LocalDateTime publishedAt;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Builder
    public NewsEntity(String headline, String linkUrl, String imageUrl, LocalDateTime publishedAt) {
        this.headline = headline;
        this.linkUrl = linkUrl;
        this.imageUrl = imageUrl;
        this.publishedAt = publishedAt;
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

        String imageUrl = request.getImageUrl();
        if (imageUrl != null) {
            this.imageUrl = imageUrl;
        }

        String publishedAt = request.getPublishedAt();
        if (publishedAt != null) {
            this.publishedAt = DateUtil.convertWithUntilMinuteString(publishedAt);
        }
    }
}
