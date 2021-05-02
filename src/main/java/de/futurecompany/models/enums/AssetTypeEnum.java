package de.futurecompany.models.enums;

import java.util.Arrays;
import java.util.Optional;

public enum AssetTypeEnum {

    IMAGE,
    VIDEO,
    OTHER;

    public static Optional<AssetTypeEnum> fromText(String pText) {
        return Arrays.stream(values())
                .filter(bl -> bl.name().equalsIgnoreCase(pText))
                .findFirst();
    }

}
