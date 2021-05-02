package de.futurecompany.models.enums;

import java.util.Arrays;
import java.util.Optional;

public enum ServiceLayerStatusProcessingEnum {

    SUCCESS(0, "Success"),
    FAILURE(1, "Exception Happened");

    private Integer codStatus;
    private String descriptionStatus;

    ServiceLayerStatusProcessingEnum(Integer pCodStatus, String pDescriptionStatus) {
        this.codStatus = pCodStatus;
        this.descriptionStatus = pDescriptionStatus;
    }

    public Integer getCodStatus() {
        return codStatus;
    }

    public String getDescriptionStatus() {
        return descriptionStatus;
    }

    public static Optional<ServiceLayerStatusProcessingEnum> fromText(String pText) {
        return Arrays.stream(values())
                .filter(bl -> bl.descriptionStatus.equalsIgnoreCase(pText))
                .findFirst();
    }

    public static Optional<ServiceLayerStatusProcessingEnum> fromValue(Integer pTargetCode) {
        return Arrays.stream(values())
                .filter(bl -> bl.codStatus.equals(pTargetCode))
                .findFirst();
    }

}
