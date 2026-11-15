package com.logtari.paynest.wallet.infrastructure.persistence;

import com.logtari.paynest.wallet.infrastructure.persistence.entity.IdempotencyRecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SpringDataIdempotencyRepository extends JpaRepository<IdempotencyRecordEntity, UUID> {
    Optional<IdempotencyRecordEntity> findByWalletIdAndIdempotencyKey(UUID walletId, String idempotencyKey);
}
