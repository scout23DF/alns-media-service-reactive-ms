package de.futurecompany.services.mappers;

import de.futurecompany.models.Asset;
import de.futurecompany.services.dtos.AssetDTO;
import de.futurecompany.services.dtos.commons.AssetInBase64FormatDTO;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Base64;

@Mapper(componentModel = "spring", uses = {})
public interface AssetMapper extends EntityMapper<AssetDTO, Asset> {

    @Mapping(source = "assetInBase64FormatDTO", target = "content", qualifiedByName = "convertAssetInBase64ToByteArray")
    @Mapping(source = "assetInBase64FormatDTO.mime", target = "mimeType")
    Asset toEntity(AssetDTO assetDTO);

    @Mapping(target = "assetInBase64FormatDTO", expression = "java(convertByteArrayToAssetInBase64(assetEntity))")
    AssetDTO toDTO(Asset assetEntity);


    @Named("convertAssetInBase64ToByteArray")
    public static byte[] convertAssetInBase64ToByteArray(AssetInBase64FormatDTO assetInBase64FormatDTO) {

        if (assetInBase64FormatDTO != null && !StringUtils.isEmpty(assetInBase64FormatDTO.getData())) {
            return Base64.getDecoder().decode(assetInBase64FormatDTO.getData());
        }

        return null;
    }

    default AssetInBase64FormatDTO convertByteArrayToAssetInBase64(Asset assetEntity) {

        if (assetEntity != null && assetEntity.getContent() != null && assetEntity.getContent().length > 0) {
            AssetInBase64FormatDTO assetInBase64FormatDTOResult = new AssetInBase64FormatDTO();
            assetInBase64FormatDTOResult.setData(Base64.getEncoder().encodeToString(assetEntity.getContent()));
            assetInBase64FormatDTOResult.setMime(assetEntity.getMimeType());
            return assetInBase64FormatDTOResult;
        }

        return null;
    }

}
