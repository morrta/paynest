package com.logtari.paynest.wallet.application.repository;

import com.logtari.paynest.wallet.domain.idempotency.IdempotencyRecord;
import com.logtari.paynest.wallet.domain.wallet.WalletId;

import java.util.Optional;

public interface IdempotencyRepository {
    IdempotencyRecord save(IdempotencyRecord record);
    Optional<IdempotencyRecord> findByWalletIdAndIdempotencyKey(WalletId walletId, String idempotencyKey);
}
