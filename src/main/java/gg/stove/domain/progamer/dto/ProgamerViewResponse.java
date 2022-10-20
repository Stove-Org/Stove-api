package gg.stove.domain.progamer.dto;

import java.io.Serializable;

import gg.stove.domain.progamer.entity.Position;
import gg.stove.domain.progamer.entity.ProgamerEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProgamerViewResponse implements Serializable {

    private Long id;

    private String name;

    private String nickname;

    private Position position;

    private String imageUrl;

    public ProgamerViewResponse(ProgamerEntity entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.nickname = entity.getNickname();
        this.position = entity.getPosition();
        this.imageUrl = entity.getImageUrl();
    }
}
