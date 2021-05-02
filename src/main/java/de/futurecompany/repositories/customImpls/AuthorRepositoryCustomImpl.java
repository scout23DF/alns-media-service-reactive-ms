package de.futurecompany.repositories.customImpls;

import de.futurecompany.models.Author;
import de.futurecompany.repositories.AuthorRepositoryCustom;
import de.futurecompany.repositories.helpers.EntityManager;
import de.futurecompany.repositories.rowmappers.AuthorRowMapper;
import io.r2dbc.spi.Row;
import io.r2dbc.spi.RowMetadata;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.sql.Column;
import org.springframework.data.relational.core.sql.Expression;
import org.springframework.data.relational.core.sql.Select;
import org.springframework.data.relational.core.sql.SelectBuilder.SelectFromAndJoin;
import org.springframework.data.relational.core.sql.Table;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.r2dbc.core.RowsFetchSpec;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.data.relational.core.query.Criteria.where;

/**
 * Spring Data SQL reactive custom repository implementation for the ArticleAuthor entity.
 */

public class AuthorRepositoryCustomImpl implements AuthorRepositoryCustom {

    private final DatabaseClient db;
    private final R2dbcEntityTemplate r2dbcEntityTemplate;
    private final EntityManager entityManager;

    private final AuthorRowMapper authorRowMapper;

    private static final Table entityTable = Table.aliased("tb_author", EntityManager.ENTITY_ALIAS);

    public AuthorRepositoryCustomImpl(R2dbcEntityTemplate template, EntityManager entityManager, AuthorRowMapper authorRowMapper) {
        this.db = template.getDatabaseClient();
        this.r2dbcEntityTemplate = template;
        this.entityManager = entityManager;
        this.authorRowMapper = authorRowMapper;
    }

    @Override
    public Flux<Author> findAllBy(Pageable pageable) {
        return findAllBy(pageable, null);
    }

    @Override
    public Flux<Author> findAllBy(Pageable pageable, Criteria criteria) {
        return createQuery(pageable, criteria).all();
    }

    RowsFetchSpec<Author> createQuery(Pageable pageable, Criteria criteria) {
        List<Expression> columns = AuthorSqlHelper.getColumns(entityTable, EntityManager.ENTITY_ALIAS);
        SelectFromAndJoin selectFrom = Select.builder().select(columns).from(entityTable);

        String select = entityManager.createSelect(selectFrom, Author.class, pageable, criteria);
        String alias = entityTable.getReferenceName().getReference();
        String selectWhere = Optional
            .ofNullable(criteria)
            .map(
                crit ->
                    new StringBuilder(select)
                        .append(" ")
                        .append("WHERE")
                        .append(" ")
                        .append(alias)
                        .append(".")
                        .append(crit.toString())
                        .toString()
            )
            .orElse(select); // TODO remove once https://github.com/spring-projects/spring-data-jdbc/issues/907 will be fixed
        return db.sql(selectWhere).map(this::process);
    }

    @Override
    public Flux<Author> findAll() {
        return findAllBy(null, null);
    }

    @Override
    public Mono<Author> findById(String id) {
        return createQuery(null, where("id").is(id)).one();
    }

    private Author process(Row row, RowMetadata metadata) {
        Author entity = authorRowMapper.apply(row, "e");
        return entity;
    }

    @Override
    public <S extends Author> Mono<S> save(S entity) {

        return this.findById(entity.getId())
                .switchIfEmpty(insert(entity))
                .then(update(entity)).thenReturn(entity);

        /*
        if (StringUtils.isEmpty(entity.getId())) {
            return insert(entity);
        } else {
            return update(entity)
                    .map(
                            numberOfUpdates -> {
                                if (numberOfUpdates.intValue() <= 0) {
                                    throw new IllegalStateException("Unable to update Country with id = " + entity.getId());
                                }
                                return entity;
                            }
                    );
        }
        */
    }

    @Override
    public <S extends Author> Mono<S> insert(S entity) {
        return entityManager.insert(entity);
    }


    @Override
    public <S extends Author> Mono<S> update(S entity) {
        return r2dbcEntityTemplate.update(entity);
    }

}

class AuthorSqlHelper {

    static List<Expression> getColumns(Table table, String columnPrefix) {
        List<Expression> columns = new ArrayList<>();

        columns.add(Column.aliased("id", table, columnPrefix + "_id"));
        columns.add(Column.aliased("ds_name", table, columnPrefix + "_ds_name"));

        return columns;
    }
}
