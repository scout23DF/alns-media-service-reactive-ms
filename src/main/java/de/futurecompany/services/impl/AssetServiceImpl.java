package de.futurecompany.services.impl;

import de.futurecompany.repositories.AssetRepository;
import de.futurecompany.services.AssetService;
import de.futurecompany.services.dtos.AssetDTO;
import de.futurecompany.services.mappers.AssetMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class AssetServiceImpl implements AssetService {

    private final AssetRepository assetRepository;
    private final AssetMapper assetMapper;

    public AssetServiceImpl(AssetRepository assetRepository,
                            AssetMapper assetMapper) {

        this.assetRepository = assetRepository;
        this.assetMapper = assetMapper;
    }

    @Override
    public Mono<AssetDTO> create(AssetDTO newAssetDTO) {

        log.debug("Request to Add one Asset: {}", newAssetDTO);

        return assetRepository.save(assetMapper.toEntity(newAssetDTO))
                              .map(assetMapper::toDTO);

    }

    @Override
    public Mono<AssetDTO> update(String assetURL, AssetDTO newAssetDTO) {

        log.debug("Request to Update one Asset: {}", newAssetDTO);

        return assetRepository.update(assetMapper.toEntity(newAssetDTO))
                              .map(assetMapper::toDTO);

    }

    @Override
    public Flux<AssetDTO> searchAll() {

        log.debug("Request to List All Assets");

        return assetRepository.findAll()
                              .map(assetMapper::toDTO);

    }

    @Override
    public Mono<AssetDTO> searchById(String assetURL) {

        log.debug("Request one Asset By Id: {}", assetURL);

        return assetRepository.findById(assetURL)
                              .map(assetMapper::toDTO);

    }

    @Override
    public Flux<AssetDTO> searchAllByAuthorId(String authorId) {

        log.debug("Request all Assets by Author Id: {}", authorId);

        return assetRepository.findByAuthorIdImpl(authorId)
                              .map(assetMapper::toDTO);

    }
}
