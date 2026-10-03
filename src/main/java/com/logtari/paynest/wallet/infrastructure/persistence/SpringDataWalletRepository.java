package com.logtari.paynest.wallet.infrastructure.persistence;

import com.logtari.paynest.wallet.infrastructure.persistence.entity.WalletEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SpringDataWalletRepository   extends JpaRepository<WalletEntity, UUID> {
}
