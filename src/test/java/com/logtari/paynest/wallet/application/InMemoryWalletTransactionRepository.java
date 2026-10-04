package com.logtari.paynest.wallet.application;

import com.logtari.paynest.wallet.application.repository.WalletTransactionRepository;
import com.logtari.paynest.wallet.domain.transaction.WalletTransaction;

import java.util.ArrayList;
import java.util.List;

/**
 * Test double that records every saved transaction so tests can assert on the history.
 */
class InMemoryWalletTransactionRepository implements WalletTransactionRepository {
    private final List<WalletTransaction> transactions = new ArrayList<>();

    @Override
    public WalletTransaction save(WalletTransaction transaction) {
        transactions.add(transaction);
        return transaction;
    }

    List<WalletTransaction> findAll() {
        return List.copyOf(transactions);
    }
}
