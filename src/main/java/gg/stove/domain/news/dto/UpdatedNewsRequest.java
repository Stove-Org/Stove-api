package gg.stove.domain.news.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UpdatedNewsRequest {

    private String headline;

    private String linkUrl;

    private String imageUrl;

    private Long hotNewsWeight;

    @Schema(type = "string", example = "2022-10-17 14:44")
    private String publishedAt;
}
