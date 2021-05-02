package de.futurecompany.repositories.rowmappers;

import de.futurecompany.models.Asset;
import de.futurecompany.models.Author;
import de.futurecompany.models.enums.AssetTypeEnum;
import de.futurecompany.repositories.helpers.ColumnConverter;
import io.r2dbc.spi.Row;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.function.BiFunction;

/**
 * Converter between {@link Row} to {@link Asset}, with proper type conversions.
 */
@Service
public class AssetRowMapper implements BiFunction<Row, String, Asset> {

    private final ColumnConverter converter;

    public AssetRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link Author} stored in the database.
     */
    @Override
    public Asset apply(Row row, String prefix) {

        return Asset.builder()
                    .url(converter.fromRow(row, prefix + "_ds_url", String.class))
                    .type(converter.fromRow(row, prefix + "_tp_asset", String.class))
                    .caption(converter.fromRow(row, prefix + "_ds_caption", String.class))
                    .publishingPrice(converter.fromRow(row, prefix + "_vl_publishing_price", BigDecimal.class))
                    .paymentStatus(converter.fromRow(row, prefix + "_st_payment", String.class))
                    .paidOn(converter.fromRow(row, prefix + "_dt_payment", LocalDateTime.class))
                    .authorId(converter.fromRow(row, prefix + "_author_id", String.class))
                    .mimeType(converter.fromRow(row, prefix + "_ds_mime_type", String.class))
                    .content(converter.fromRow(row, prefix + "_bl_content", byte[].class))
                    .build();

    }
}
