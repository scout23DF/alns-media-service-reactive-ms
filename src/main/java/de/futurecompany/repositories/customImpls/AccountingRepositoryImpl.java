package de.futurecompany.repositories.customImpls;

import de.futurecompany.models.RoyaltiesAssetsAuthorSummary;
import de.futurecompany.repositories.AccountingRepository;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.math.BigDecimal;

@Repository
public class AccountingRepositoryImpl implements AccountingRepository {

    private final DatabaseClient databaseClient;

    private static final String SQL_ROYALTIES_ASSETS_BY_AUTHOR =
         "select" +
           " year(article.DT_PUBLISHING)                 as referenceYear, " +
           " monthname(article.DT_PUBLISHING)            as referenceMonth, " +
         //  " year(refasset.DT_REFERENCE_START)           as referenceYear, " +
         //  " monthname(refasset.DT_REFERENCE_START)      as referenceMonth, " +
           " author.ID                                   as authorId, " +
           " author.DS_NAME                              as authorName, " +
           " count(asset.DS_URL)                         as amountOfAssetsReferencedInPublishedArticles, " +
           " sum(asset.VL_PUBLISHING_PRICE)              as totalRoyaltiesValue " +
          "from " +
           "   TB_AUTHOR author " +
           "       inner join TB_ASSET asset on asset.AUTHOR_ID = author.ID " +
           "       inner join TB_REFERENCED_ASSET refasset on refasset.ASSET_URL = asset.DS_URL " +
           "       inner join TB_NEWS_ARTICLE article on article.ID = refasset.ARTICLE_ID " +
          "where 1 = 1 " +
          "  and article.IS_PUBLISHED = TRUE " +
          "  and article.DT_PUBLISHING <= current_timestamp() " +
         // "   and refasset.DT_REFERENCE_START <= current_timestamp() " +
         "group by " +
         "  referenceYear, " +
         "  referenceMonth, " +
         "  authorId, " +
         "  authorName " +
         "order by " +
         "  referenceYear, " +
         "  referenceMonth, " +
         "  authorName ";

    public AccountingRepositoryImpl(DatabaseClient databaseClient) {
        this.databaseClient = databaseClient;
    }

    @Override
    public Flux<RoyaltiesAssetsAuthorSummary> findAll() {

        return databaseClient.sql(SQL_ROYALTIES_ASSETS_BY_AUTHOR)
                             .fetch()
                             .all()
                             // .bufferUntilChanged( result -> result.get("authorId"))
                             .map(theRowColumnList -> {
                                 RoyaltiesAssetsAuthorSummary.RoyaltiesAssetsAuthorSummaryBuilder royaltiesSummary = RoyaltiesAssetsAuthorSummary.builder();

                                 royaltiesSummary.referenceYear((Integer) theRowColumnList.get("referenceYear"));
                                 royaltiesSummary.referenceMonth(String.valueOf(theRowColumnList.get("referenceMonth")));
                                 royaltiesSummary.authorId(String.valueOf(theRowColumnList.get("authorId")));
                                 royaltiesSummary.authorName(String.valueOf(theRowColumnList.get("authorName")));
                                 royaltiesSummary.amountOfAssetsReferencedInPublishedArticles((Long) theRowColumnList.get("amountOfAssetsReferencedInPublishedArticles"));
                                 royaltiesSummary.totalRoyaltiesValue(new BigDecimal(String.valueOf(theRowColumnList.get("totalRoyaltiesValue"))));

                                 return royaltiesSummary.build();

                            });
    }

}
