package gg.stove.domain.news.dto;

import java.util.HashSet;
import java.util.Set;
import javax.validation.constraints.NotBlank;

import gg.stove.domain.news.entity.NewsEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import static gg.stove.utils.DateUtil.convertWithUntilMinuteString;

@Getter
@Builder
public class CreateNewsRequest {

    @NotBlank
    private String headline;

    @NotBlank
    private String linkUrl;

    @NotBlank
    private String imgUrl;

    @NotBlank
    @Schema(type = "string", example = "2022-10-17 14:44")
    private String uploadedAt;

    private final Boolean isPublished = false;

    private final Set<String> hashtags = new HashSet<>();

    public NewsEntity toNewsEntity() {
        return NewsEntity.builder()
            .headline(headline)
            .linkUrl(linkUrl)
            .imgUrl(imgUrl)
            .uploadedAt(convertWithUntilMinuteString(uploadedAt))
            .isPublished(isPublished)
            .build();
    }
}
