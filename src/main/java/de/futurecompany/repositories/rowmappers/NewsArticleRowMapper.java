package de.futurecompany.repositories.rowmappers;

import de.futurecompany.models.NewsArticle;
import de.futurecompany.repositories.helpers.ColumnConverter;
import io.r2dbc.spi.Row;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.function.BiFunction;

/**
 * Converter between {@link Row} to {@link NewsArticle}, with proper type conversions.
 */
@Service
public class NewsArticleRowMapper implements BiFunction<Row, String, NewsArticle> {

    private final ColumnConverter converter;

    public NewsArticleRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link NewsArticle} stored in the database.
     */
    @Override
    public NewsArticle apply(Row row, String prefix) {

        return NewsArticle.builder()
                          .articleId(converter.fromRow(row, prefix + "_id", String.class))
                          .title(converter.fromRow(row, prefix + "_ds_title", String.class))
                          .fullText(converter.fromRow(row, prefix + "_tx_article", String.class))
                          .published(converter.fromRow(row, prefix + "_is_published", Boolean.class))
                          .publishingDateTime(converter.fromRow(row, prefix + "_dt_publishing", LocalDateTime.class))
                          .authorId(converter.fromRow(row, prefix + "_author_id", String.class))
                          .build();


    }
}
