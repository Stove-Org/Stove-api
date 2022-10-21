package gg.stove.domain.progamer.dto;

import gg.stove.domain.progamer.entity.Career;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CareerDto {

    private Integer worlds;

    private Integer msi;

    private Integer lck;

    public CareerDto(Career career) {
        this.worlds = career.getWorlds();
        this.msi = career.getMsi();
        this.lck = career.getLck();
    }

    public Career toEntity() {
        return new Career(worlds, msi, lck);
    }
}
