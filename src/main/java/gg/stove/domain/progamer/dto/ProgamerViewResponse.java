package gg.stove.domain.progamer.dto;

import java.io.Serializable;
import java.time.LocalDate;

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

    private String imgUrl;

    private String birthday;

    private CareerDto career;

    public ProgamerViewResponse(ProgamerEntity entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.nickname = entity.getNickname();
        this.position = entity.getPosition();
        this.imgUrl = entity.getImgUrl();
        this.birthday = entity.getBirthday().toString();
        this.career = new CareerDto(entity.getCareer());
    }
}
