package de.futurecompany.services.dtos;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import de.futurecompany.models.enums.PaymentStatusEnum;
import de.futurecompany.services.dtos.commons.AssetInBase64FormatDTO;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder(builderClassName = "AssetDTOBuilder", toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE) //Hides the constructor to force useage of the Builder.
@JsonDeserialize(builder = AssetDTO.AssetDTOBuilder.class)
public class AssetDTO {

    private final String url;
    private final String type;
    private final String caption;
    private final BigDecimal publishingPrice;
    private String paymentStatus = PaymentStatusEnum.NOT_PAID.name();;
    private LocalDateTime paidOn;
    private AssetInBase64FormatDTO assetInBase64FormatDTO;

    private final String authorId;
    private final String authorName;


    @JsonPOJOBuilder(withPrefix = "")
    public static class AssetDTOBuilder {
    }

}
