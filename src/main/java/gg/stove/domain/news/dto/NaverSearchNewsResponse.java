package gg.stove.domain.news.dto;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class NaverSearchNewsResponse {

    private String lastBuildDate;
    private Long total;
    private Integer start;
    private Integer display;
    private List<NaverSearchNewsResponseItem> items;
}
