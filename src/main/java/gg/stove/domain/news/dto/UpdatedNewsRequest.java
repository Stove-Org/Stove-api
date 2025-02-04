package gg.stove.domain.news.dto;

import java.util.HashSet;
import java.util.Set;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UpdatedNewsRequest {

    private String headline;

    private String linkUrl;

    private String imgUrl;

    @Schema(type = "string", example = "2022-10-17 14:44")
    private String uploadedAt;

    private Boolean isPublished;

    private Long viewsCount;

    private final Set<String> hashtags = new HashSet<>();
}
