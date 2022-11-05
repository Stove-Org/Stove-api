package gg.stove.domain.hashtags.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class HashtagView {

    private final Long id;
    private final String hashtag;

    @QueryProjection
    public HashtagView(Long id, String hashtag) {
        this.id = id;
        this.hashtag = hashtag;
    }
}
