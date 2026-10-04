package com.logtari.paynest.wallet.infrastructure.persistence;

import com.logtari.paynest.wallet.application.repository.WalletTransactionRepository;
import com.logtari.paynest.wallet.domain.transaction.WalletTransaction;
import com.logtari.paynest.wallet.infrastructure.persistence.mapper.WalletTransactionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostgresWalletTransactionRepository implements WalletTransactionRepository {

    private final SpringDataWalletTransactionRepository repository;
    private final WalletTransactionMapper mapper;

    @Override
    public WalletTransaction save(WalletTransaction transaction) {
        var entity = mapper.toEntity(transaction);
        var saved = repository.save(entity);

        return mapper.toDomain(saved);
    }
}
