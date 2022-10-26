package gg.stove.domain.nextlck.dto;

import javax.validation.constraints.NotBlank;

import gg.stove.domain.progamer.entity.Position;
import gg.stove.domain.team.Team;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NextLckSaveRequest {

    @NotBlank
    private Team team;

    @NotBlank
    private Position position;

    @NotBlank
    private Long progamerId;
}
