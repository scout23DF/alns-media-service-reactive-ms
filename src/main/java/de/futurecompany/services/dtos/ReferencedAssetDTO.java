package de.futurecompany.services.dtos;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder(builderClassName = "ReferencedAssetDTOBuilder", toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE) //Hides the constructor to force useage of the Builder.
@JsonDeserialize(builder = ReferencedAssetDTO.ReferencedAssetDTOBuilder.class)
public class ReferencedAssetDTO {

    private Long id;
    private String articleId;
    private String assetURL;
    private Instant referenceStartedOn = Instant.now();
    private Instant referenceEndedOn;
    private Integer qtyViews = 0;

    private String articleTitle;
    private String articleAuthorName;

    private String assetCaption;
    private String assetAuthorName;

    @JsonPOJOBuilder(withPrefix = "")
    public static class ReferencedAssetDTOBuilder {
    }

}
