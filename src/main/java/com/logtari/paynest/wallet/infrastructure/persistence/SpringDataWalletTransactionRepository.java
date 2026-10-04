package com.logtari.paynest.wallet.infrastructure.persistence;

import com.logtari.paynest.wallet.infrastructure.persistence.entity.WalletTransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SpringDataWalletTransactionRepository extends JpaRepository<WalletTransactionEntity, UUID> {
    List<WalletTransactionEntity> findByWalletIdOrderByCreatedAtDesc(UUID walletId);
}
