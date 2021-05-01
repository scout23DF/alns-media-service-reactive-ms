package de.futurecompany.repositories.rowmappers;

import de.futurecompany.models.ArticleAuthor;
import de.futurecompany.repositories.helpers.ColumnConverter;
import io.r2dbc.spi.Row;
import org.springframework.stereotype.Service;

import java.util.function.BiFunction;

/**
 * Converter between {@link Row} to {@link ArticleAuthor}, with proper type conversions.
 */
@Service
public class ArticleAuthorRowMapper implements BiFunction<Row, String, ArticleAuthor> {

    private final ColumnConverter converter;

    public ArticleAuthorRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link ArticleAuthor} stored in the database.
     */
    @Override
    public ArticleAuthor apply(Row row, String prefix) {

        return ArticleAuthor.builder()
                            .id(converter.fromRow(row, prefix + "_id", String.class))
                            .name(converter.fromRow(row, prefix + "_ds_name", String.class))
                            .build();

    }
}
