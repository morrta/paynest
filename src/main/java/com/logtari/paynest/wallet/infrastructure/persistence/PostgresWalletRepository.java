package com.logtari.paynest.wallet.infrastructure.persistence;

import com.logtari.paynest.wallet.application.repository.WalletRepository;
import com.logtari.paynest.wallet.domain.wallet.Wallet;
import com.logtari.paynest.wallet.domain.wallet.WalletId;
import com.logtari.paynest.wallet.infrastructure.persistence.mapper.WalletPersistenceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PostgresWalletRepository implements WalletRepository {

    private final SpringDataWalletRepository repository;
    private final WalletPersistenceMapper mapper;

    @Override
    public Wallet save(Wallet wallet) {
        return null;
    }

    @Override
    public Optional<Wallet> findById(WalletId walletId) {
        return Optional.empty();
    }
}
