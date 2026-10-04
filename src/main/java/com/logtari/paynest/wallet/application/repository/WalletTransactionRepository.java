package com.logtari.paynest.wallet.application.repository;

import com.logtari.paynest.wallet.domain.transaction.WalletTransaction;

public interface WalletTransactionRepository {
    WalletTransaction save(WalletTransaction transaction);
}
