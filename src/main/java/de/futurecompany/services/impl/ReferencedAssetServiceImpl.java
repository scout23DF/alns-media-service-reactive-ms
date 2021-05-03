package de.futurecompany.services.impl;

import de.futurecompany.repositories.ReferencedAssetRepository;
import de.futurecompany.services.ReferencedAssetService;
import de.futurecompany.services.dtos.ReferencedAssetDTO;
import de.futurecompany.services.mappers.ReferencedAssetMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class ReferencedAssetServiceImpl implements ReferencedAssetService {

    private final ReferencedAssetRepository referencedAssetRepository;
    private final ReferencedAssetMapper referencedAssetMapper;

    public ReferencedAssetServiceImpl(ReferencedAssetRepository referencedAssetRepository,
                                      ReferencedAssetMapper referencedAssetMapper) {

        this.referencedAssetRepository = referencedAssetRepository;
        this.referencedAssetMapper = referencedAssetMapper;
    }

    @Override
    public Mono<ReferencedAssetDTO> create(ReferencedAssetDTO referencedAssetToAddDTO) {

        log.debug("Request to Add one ReferencedAsset: {}", referencedAssetToAddDTO);

        return referencedAssetRepository.save(referencedAssetMapper.toEntity(referencedAssetToAddDTO))
                                        .map(referencedAssetMapper::toDTO);

    }

    @Override
    public Mono<ReferencedAssetDTO> update(Long pId, ReferencedAssetDTO referencedAssetToUpdateDTO) {

        log.debug("Request to Update one ReferencedAsset: {}", referencedAssetToUpdateDTO);

        return referencedAssetRepository.update(referencedAssetMapper.toEntity(referencedAssetToUpdateDTO))
                                        .map(referencedAssetMapper::toDTO);

    }

    @Override
    public Flux<ReferencedAssetDTO> searchAll() {

        log.debug("Request to List All ReferencedAssets");

        return referencedAssetRepository.findAll()
                                        .map(referencedAssetMapper::toDTO);

    }

    @Override
    public Mono<ReferencedAssetDTO> searchById(Long pId) {

        log.debug("Request one ReferencedAsset By Id: {}", pId);

        return referencedAssetRepository.findById(pId)
                                        .map(referencedAssetMapper::toDTO);

    }

    @Override
    public Mono<ReferencedAssetDTO> searchByArticleIdAndAssetURL(String pArticleId, String pAssetURL) {

        log.debug("Request one Asset by Unique Key - Article Id: {} and Asset URL: {}", pArticleId, pAssetURL);

        return referencedAssetRepository.findByArticleIdAndAssetURL(pArticleId, pAssetURL)
                                        .switchIfEmpty(Mono.empty())
                                        .map(referencedAssetMapper::toDTO);


    }

    @Override
    public Flux<ReferencedAssetDTO> searchAllByArticleId(String pArticleId) {

        log.debug("Request all Assets by Article Id: {}", pArticleId);

        return referencedAssetRepository.findByArticleId(pArticleId)
                .map(referencedAssetMapper::toDTO);

    }

    @Override
    public Flux<ReferencedAssetDTO> searchAllByAssetURL(String pAssetURL) {

        log.debug("Request all Assets by Asset URL: {}", pAssetURL);

        return referencedAssetRepository.findByAssetURL(pAssetURL)
                                        .map(referencedAssetMapper::toDTO);

    }

}
