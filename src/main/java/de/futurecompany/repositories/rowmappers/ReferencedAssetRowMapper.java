package de.futurecompany.repositories.rowmappers;

import de.futurecompany.models.ReferencedAsset;
import de.futurecompany.repositories.helpers.ColumnConverter;
import io.r2dbc.spi.Row;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.function.BiFunction;

/**
 * Converter between {@link Row} to {@link ReferencedAsset}, with proper type conversions.
 */
@Service
public class ReferencedAssetRowMapper implements BiFunction<Row, String, ReferencedAsset> {

    private final ColumnConverter converter;

    public ReferencedAssetRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link ReferencedAsset} stored in the database.
     */
    @Override
    public ReferencedAsset apply(Row row, String prefix) {

        return ReferencedAsset.builder()
                              .id(converter.fromRow(row, prefix + "_id", Long.class))
                              .articleId(converter.fromRow(row, prefix + "_article_id", String.class))
                              .assetURL(converter.fromRow(row, prefix + "_asset_url", String.class))
                              .referenceStartedOn(converter.fromRow(row, prefix + "_dt_reference_start", Instant.class))
                              .referenceEndedOn(converter.fromRow(row, prefix + "_dt_reference_end", Instant.class))
                              .qtyViews(converter.fromRow(row, prefix + "_qt_views", Integer.class))
                              .build();

    }
}
