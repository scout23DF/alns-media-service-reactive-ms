package de.futurecompany.models;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class RoyaltiesAssetsAuthorSummary {

    private Integer referenceYear;
    private String referenceMonth;
    private String authorId;
    private String authorName;
    private Long amountOfAssetsReferencedInPublishedArticles;
    private BigDecimal totalRoyaltiesValue;

}
