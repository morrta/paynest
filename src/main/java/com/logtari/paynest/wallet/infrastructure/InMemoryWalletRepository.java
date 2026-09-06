package com.logtari.paynest.wallet.infrastructure;

import com.logtari.paynest.wallet.application.repository.WalletRepository;
import com.logtari.paynest.wallet.domain.Wallet;
import com.logtari.paynest.wallet.domain.WalletId;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryWalletRepository implements WalletRepository {
    private final Map<WalletId, Wallet> wallets = new ConcurrentHashMap<>();

    @Override
    public Wallet save(Wallet wallet) {
        wallets.put(wallet.getWalletId(), wallet);
        return wallet;
    }

    @Override
    public Optional<Wallet> findById(WalletId walletId) {
        return Optional.ofNullable(wallets.get(walletId));
    }
}
