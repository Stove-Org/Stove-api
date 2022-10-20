package gg.stove.domain.progamer.entity;

public enum Position {

    TOP, JUNGLE, MID, AD, SUPPORTER;

    public static Position of(String position) {
        Position[] values = values();
        for (Position value : values) {
          if (value.name().equals(position.toUpperCase())) {
              return value;
          }
        }
        throw new IllegalArgumentException("position은 top, mid, jungle, ad, support만 들어올 수 있습니다. 입력된 position: " + position);
    }
}
