package br.com.onebr.enumeration;

import java.util.Arrays;
import lombok.Getter;

@Getter
public enum BacteriaType {

    EC_BR(1),
    KP_BR(2),
    SE_BR(3);

    long id;

    BacteriaType(int id) {
        this.id = id;
    }

    public static BacteriaType fromString(String name) {
        return Arrays.stream(BacteriaType.values())
            .filter(v -> v.name().equals(name.toUpperCase()))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("unknown value: " + name));
    }

    public static BacteriaType fromId(long id) {
        return Arrays.stream(BacteriaType.values())
            .filter(v -> v.getId() == id)
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("unknown value: " + id));
    }
}
