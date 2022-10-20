package gg.stove.domain.progamer.dto;

import gg.stove.domain.progamer.entity.Position;
import gg.stove.domain.progamer.entity.ProgamerEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UpdateProgamerRequest {

    private String name;

    private String nickname;

    private String position;

    private String imageUrl;

    public ProgamerEntity toProgamerEntity() {
        return ProgamerEntity.builder()
            .name(name)
            .nickname(nickname)
            .position(Position.of(position))
            .imageUrl(imageUrl)
            .build();
    }
}
