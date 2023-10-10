package ru.textparser.dhtextparserproject.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Enum для общих полезных значений и констант
 */
@Getter
@AllArgsConstructor
public enum CommonUtils {

    SEPARATOR(":");
    private final String val;

    public String getVal () {
        return val;
    }
}
