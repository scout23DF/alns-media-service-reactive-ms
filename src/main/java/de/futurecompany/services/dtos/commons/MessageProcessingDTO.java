package de.futurecompany.services.dtos.commons;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import de.futurecompany.models.enums.MessageProcessingTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class MessageProcessingDTO implements IGenericBaseDTO {

    private MessageProcessingTypeEnum messageProcessingType;
    private HttpStatus httpStatusResponse;
    private String itemMeaningfulId;
    private String title;
    private String description;

    @JsonIgnoreProperties(value = { "suppressed" })
    private Throwable exceptionOccurred;

}
