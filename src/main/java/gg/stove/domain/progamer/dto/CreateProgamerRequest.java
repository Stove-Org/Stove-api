package gg.stove.domain.progamer.dto;

import java.time.LocalDate;
import java.util.Set;
import javax.validation.constraints.NotBlank;

import gg.stove.domain.progamer.entity.Position;
import gg.stove.domain.progamer.entity.ProgamerEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateProgamerRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String nickname;

    @NotBlank
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
