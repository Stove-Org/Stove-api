package gg.stove.domain.progamer.dto;

import java.time.LocalDate;
import java.util.Set;

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

    private String imgUrl;

    private LocalDate birthday;

    private CareerDto career;

    private Set<String> alias;

    public ProgamerEntity toProgamerEntity() {
        return ProgamerEntity.builder()
            .name(name)
            .nickname(nickname)
            .position(Position.of(position))
            .imgUrl(imgUrl)
            .birthday(birthday)
            .career(career.toEntity())
            .alias(alias)
            .build();
    }
}
