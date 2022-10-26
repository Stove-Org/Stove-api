package gg.stove.domain.progamer.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import gg.stove.domain.progamer.entity.Position;
import gg.stove.domain.progamer.entity.ProgamerEntity;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ProgamerViewResponse implements Serializable {

    private Long id;

    private String name;

    private String nickname;

    private Position position;

    private String imgUrl;

    private String birthday;

    private CareerDto career;
    private Set<String> alias = new HashSet<>();

    public ProgamerViewResponse(ProgamerEntity entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.nickname = entity.getNickname();
        this.position = entity.getPosition();
        this.imgUrl = entity.getImgUrl();
        this.birthday = entity.getBirthday().toString();
        this.career = new CareerDto(entity.getCareer());
        this.alias = entity.getAlias();
    }
}
