package de.futurecompany.services.mappers;

import de.futurecompany.models.Asset;
import de.futurecompany.models.ReferencedAsset;
import de.futurecompany.services.dtos.AssetDTO;
import de.futurecompany.services.dtos.ReferencedAssetDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {})
public interface ReferencedAssetMapper extends EntityMapper<ReferencedAssetDTO, ReferencedAsset> {

    @Mapping(source = "newsArticle.title", target = "articleTitle")
    @Mapping(source = "newsArticle.author.name", target = "articleAuthorName")
    @Mapping(source = "assetReferenced.caption", target = "assetCaption")
    @Mapping(source = "assetReferenced.author.name", target = "assetAuthorName")
    ReferencedAssetDTO toDTO(ReferencedAsset assetEntity);

}
