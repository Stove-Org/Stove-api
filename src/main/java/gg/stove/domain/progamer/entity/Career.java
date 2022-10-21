package gg.stove.domain.progamer.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Career {
    @Column(name = "worlds")
    private Integer worlds = 0;
    @Column(name = "msi")
    private Integer msi = 0;
    @Column(name = "lck")
    private Integer lck = 0;

    public Career(Integer worlds, Integer msi, Integer lck) {
        this.worlds = worlds;
        this.msi = msi;
        this.lck = lck;
    }
}
