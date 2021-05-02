package de.futurecompany.models;

import de.futurecompany.models.enums.PaymentStatusEnum;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Table("tb_asset")
@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE) // Hides the constructor to force useage of the Builder.
public class Asset {

    // { url: string, caption: string, imageAuthor: string, price: int}

    @Id
    @Column("ds_url")
    @Size(max = 512)
    @Setter(AccessLevel.NONE)
    private String url;

    @Column("tp_asset")
    @Size(max = 10)
    @NotNull
    private String type;

    @Column("ds_caption")
    @NotNull
    @Size(max = 255)
    private String caption;

    @Column("vl_publishing_price")
    private BigDecimal publishingPrice;

    @Column("st_payment")
    private String paymentStatus = PaymentStatusEnum.NOT_PAID.name();

    @Column("dt_payment")
    private LocalDateTime paidOn;

    @Column("author_id")
    private String authorId;

    @Column("ds_mime_type")
    private String mimeType;

    @Column("bl_content")
    private byte[] content;


    @Transient
    private Author author;

}
