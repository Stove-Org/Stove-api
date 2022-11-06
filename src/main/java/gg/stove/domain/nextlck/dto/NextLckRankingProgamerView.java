package gg.stove.domain.nextlck.dto;

import java.io.Serializable;

import gg.stove.domain.progamer.dto.CareerDto;
import gg.stove.domain.progamer.entity.ProgamerEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NextLckRankingProgamerView implements Serializable {

    private Long id;

    private String name;

    private String nickname;
    private String imgUrl;

    public NextLckRankingProgamerView(ProgamerEntity entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.nickname = entity.getNickname();
        this.imgUrl = entity.getImgUrl();
    }
}
