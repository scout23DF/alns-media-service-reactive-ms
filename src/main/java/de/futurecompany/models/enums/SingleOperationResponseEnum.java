package de.futurecompany.models.enums;

import java.util.Arrays;
import java.util.Optional;

public enum SingleOperationResponseEnum {

    SUCCESS(0, "SUCCESS/DONE"),
    FAILURE(1, "FAILURE/NOT-DONE"),
    TRUE(2, "TRUE"),
    FALSE(3, "FALSE");

    private final Integer codStatus;
    private final String descriptionStatus;

    SingleOperationResponseEnum(Integer pCodStatus, String pDescriptionStatus) {
        this.codStatus = pCodStatus;
        this.descriptionStatus = pDescriptionStatus;
    }

    public Integer getCodStatus() {
        return codStatus;
    }

    public String getDescriptionStatus() {
        return descriptionStatus;
    }

    public static Optional<SingleOperationResponseEnum> fromText(String pText) {
        return Arrays.stream(values())
                .filter(bl -> bl.descriptionStatus.equalsIgnoreCase(pText))
                .findFirst();
    }

    public static Optional<SingleOperationResponseEnum> fromValue(Integer pTargetCode) {
        return Arrays.stream(values())
                .filter(bl -> bl.codStatus.equals(pTargetCode))
                .findFirst();
    }

}
