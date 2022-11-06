package gg.stove.domain.hashtags.dto;

import java.io.Serializable;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class HashtagView implements Serializable {

    private Long id;
    private String hashtag;

    @QueryProjection
    public HashtagView(Long id, String hashtag) {
        this.id = id;
        this.hashtag = hashtag;
    }
}
