package de.futurecompany.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;

@Table("tb_referenced_asset")
@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE) // Hides the constructor to force useage of the Builder.
public class ReferencedAsset {

    @Id
    @Column("id")
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column("article_id")
    @Size(max = 60)
    @NotNull
    private String articleId;

    @Column("asset_url")
    @Size(max = 512)
    @NotNull
    private String assetURL;

    @Column("dt_reference_start")
    private Instant referenceStartedOn = Instant.now();

    @Column("dt_reference_end")
    private Instant referenceEndedOn;

    @Column("qt_views")
    private Integer qtyViews = 0;


    @Transient
    private NewsArticle newsArticle;

    @Transient
    private Asset assetReferenced;

}
