package gg.stove.domain.team.entity;

public enum TeamName {

    GEN, T1, LSB, DK, KT, DRX, KDF, NS, BRO, HLE;

    public static TeamName of(String name) {
        TeamName[] values = values();
        for (TeamName value : values) {
          if (value.name().equals(name.toUpperCase())) {
              return value;
          }
        }
        throw new IllegalArgumentException("팀 이름은 GEN, T1, LSB, DK, KT, DRX, KDF, NS, BRO, HLE만 들어올 수 있습니다. 입력된 name: " + name);
    }
}
