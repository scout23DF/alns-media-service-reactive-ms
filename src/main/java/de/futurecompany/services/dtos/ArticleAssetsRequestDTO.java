package de.futurecompany.services.dtos;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import de.futurecompany.config.ConstantsApp;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Builder(builderClassName = "AddedAssetDTOBuilder", toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE) //Hides the constructor to force useage of the Builder.
@JsonDeserialize(builder = ArticleAssetsRequestDTO.AddedAssetDTOBuilder.class)
public class ArticleAssetsRequestDTO {

    @Pattern(regexp = ConstantsApp.DATE_AS_STRING_PATTERN)
    private String referenceStartedOn;

    @Pattern(regexp = ConstantsApp.DATE_AS_STRING_PATTERN)
    private String referenceEndedOn;

    @Size(min = 1)
    private List<String> assetsURLToReferenceList;

    @JsonPOJOBuilder(withPrefix = "")
    public static class AddedAssetDTOBuilder {
    }

}
