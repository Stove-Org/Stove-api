package gg.stove.domain.news.dto;

import javax.validation.constraints.NotBlank;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UpdatedNewsRequest {

    @NotBlank
    private String headline;

    @NotBlank
    private String linkUrl;

    @NotBlank
    private String imageUrl;

    private Long hotNewsWeight;

    @NotBlank
    @Schema(type = "string", example = "2022-10-17 14:44")
    private String publishedAt;
}
