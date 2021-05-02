package de.futurecompany.repositories.rowmappers;

import de.futurecompany.models.Author;
import de.futurecompany.repositories.helpers.ColumnConverter;
import io.r2dbc.spi.Row;
import org.springframework.stereotype.Service;

import java.util.function.BiFunction;

/**
 * Converter between {@link Row} to {@link Author}, with proper type conversions.
 */
@Service
public class AuthorRowMapper implements BiFunction<Row, String, Author> {

    private final ColumnConverter converter;

    public AuthorRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link Author} stored in the database.
     */
    @Override
    public Author apply(Row row, String prefix) {

        return Author.builder()
                            .id(converter.fromRow(row, prefix + "_id", String.class))
                            .name(converter.fromRow(row, prefix + "_ds_name", String.class))
                            .build();

    }
}
