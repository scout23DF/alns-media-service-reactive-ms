package de.futurecompany.models.enums;

import java.util.Arrays;
import java.util.Optional;

public enum AppOperationTypeEnum {

    PUBLISH_ARTICLE(10, "Publish Article"),
    UNPUBLISH_ARTICLE(11, "Unpublish Article"),
    ADD_REFERENCED_ASSETS_TO_ARTICLE(20, "Add Referenced Asstes to an Article");

    private Integer codStatus;
    private String descriptionStatus;

    AppOperationTypeEnum(Integer pCodStatus, String pDescriptionStatus) {
        this.codStatus = pCodStatus;
        this.descriptionStatus = pDescriptionStatus;
    }

    public Integer getCodStatus() {
        return codStatus;
    }

    public String getDescriptionStatus() {
        return descriptionStatus;
    }

    public static Optional<AppOperationTypeEnum> fromText(String pText) {
        return Arrays.stream(values())
                .filter(bl -> bl.descriptionStatus.equalsIgnoreCase(pText))
                .findFirst();
    }

    public static Optional<AppOperationTypeEnum> fromValue(Integer pTargetCode) {
        return Arrays.stream(values())
                .filter(bl -> bl.codStatus.equals(pTargetCode))
                .findFirst();
    }

}
