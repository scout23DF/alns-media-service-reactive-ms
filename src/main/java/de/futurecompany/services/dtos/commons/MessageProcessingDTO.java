package de.futurecompany.services.dtos.commons;

import de.futurecompany.models.enums.MessageProcessingTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MessageProcessingDTO implements IGenericBaseDTO {

    private MessageProcessingTypeEnum messageProcessingType;
    private String title;
    private String description;
    private Throwable exceptionOccurred;

}
