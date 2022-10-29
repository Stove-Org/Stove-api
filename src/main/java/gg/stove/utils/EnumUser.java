package gg.stove.utils;

import lombok.Getter;

@Getter
public enum EnumUser {

    DEFAULT_USER(22L);
    private final Long id;

    EnumUser(Long id) {
        this.id = id;
    }
}
