package gg.stove.domain.team;

import lombok.Getter;

@Getter
public enum Team {

    GEN(1, "GEN"),
    T1(2, "T1"),
    LSB(3, "LSB"),
    DK(4, "DK"),
    KT(5, "KT"),
    DRX(6, "DRX"),
    KDF(7, "KDF"),
    NS(8, "NS"),
    BRO(9, "BRO"),
    HLE(10, "HLE");

    private final Integer id;
    private final String name;

    Team(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public static Team of(Integer id) {
        Team[] values = values();
        for (Team value : values) {
            if (value.id.equals(id)) {
                return value;
            }
        }
        throw new IllegalArgumentException("팀 Id는 1 ~ 10 사이의 값이여야 합니다.");
    }

    public static Team of(String name) {
        Team[] values = values();
        for (Team value : values) {
          if (value.name().equals(name.toUpperCase())) {
              return value;
          }
        }
        throw new IllegalArgumentException("팀 이름은 GEN, T1, LSB, DK, KT, DRX, KDF, NS, BRO, HLE만 들어올 수 있습니다. 입력된 name: " + name);
    }
}
