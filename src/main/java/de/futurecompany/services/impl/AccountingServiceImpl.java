package de.futurecompany.services.impl;

import de.futurecompany.models.RoyaltiesAssetsAuthorSummary;
import de.futurecompany.repositories.AccountingRepository;
import de.futurecompany.services.AccountingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@Slf4j
public class AccountingServiceImpl implements AccountingService {

    private final AccountingRepository accountingRepository;

    public AccountingServiceImpl(AccountingRepository accountingRepository) {

        this.accountingRepository = accountingRepository;
    }

    @Override
    public Flux<RoyaltiesAssetsAuthorSummary> searchAll() {

        log.debug("Request to List All RoyaltiesAssetsAuthorSummary");

        return accountingRepository.findAll();

    }

}
