package gg.stove.domain.news.entity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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

    @Column(name = "is_published")
    private Boolean isPublished = false;

    @Column(name = "uploaded_at")
    private LocalDateTime uploadedAt;

    @OneToMany(mappedBy = "newsEntity")
    private Set<NewsHashtagEntity> newsHashtagEntities = new HashSet<>();

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Builder
    public NewsEntity(String headline, String linkUrl, String imgUrl, LocalDateTime uploadedAt, Boolean isPublished) {
        this.headline = headline;
        this.linkUrl = linkUrl;
        this.imgUrl = imgUrl;
        this.uploadedAt = uploadedAt;
        this.isPublished = isPublished;
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

        String uploadedAt = request.getUploadedAt();
        if (uploadedAt != null) {
            this.uploadedAt = DateUtil.convertWithUntilMinuteString(uploadedAt);
        }

        Boolean isPublished = request.getIsPublished();
        if (isPublished != null) {
            this.isPublished = isPublished;
        }

        Long viewCount = request.getViewsCount();
        if (viewCount != null) {
            this.viewCount = viewCount;
        }
    }

    public void increaseViewCount() {
        this.viewCount++;
    }
}
