package gg.stove.domain.nextlck.dto;

import java.time.LocalDate;

import gg.stove.domain.progamer.dto.CareerDto;
import gg.stove.domain.progamer.entity.Position;
import gg.stove.domain.progamer.entity.ProgamerEntity;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Builder
@Getter
@EqualsAndHashCode
public class NextLckPlayerResponse {
    private Long id;
    private String name;
    private String nickname;
    private String imgUrl;
    private Position position;
    private LocalDate birthday;
    private CareerDto career;

    public static NextLckPlayerResponse from(ProgamerEntity progamer) {
        if (progamer == null) {
            return null;
        }

        return NextLckPlayerResponse.builder()
            .id(progamer.getId())
            .name(progamer.getName())
            .nickname(progamer.getNickname())
            .imgUrl(progamer.getImgUrl())
            .position(progamer.getPosition())
            .birthday(progamer.getBirthday())
            .career(new CareerDto(progamer.getCareer()))
            .build();
    }
}
