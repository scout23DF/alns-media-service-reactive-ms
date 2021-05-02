package de.futurecompany.models.enums;

import java.util.Arrays;
import java.util.Optional;

public enum PaymentStatusEnum {

    NOT_PAID,
    UNDER_NEGOTIATION,
    BILLING,
    PROCESSING,
    CANCELLED,
    RETURNED,
    PAID;

    public static Optional<PaymentStatusEnum> fromText(String pText) {
        return Arrays.stream(values())
                .filter(bl -> bl.name().equalsIgnoreCase(pText))
                .findFirst();
    }

}
