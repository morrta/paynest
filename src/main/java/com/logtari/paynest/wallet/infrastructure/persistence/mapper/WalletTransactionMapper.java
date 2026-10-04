package com.logtari.paynest.wallet.infrastructure.persistence.mapper;

import com.logtari.paynest.wallet.domain.transaction.WalletTransaction;
import com.logtari.paynest.wallet.domain.wallet.Currency;
import com.logtari.paynest.wallet.domain.wallet.Money;
import com.logtari.paynest.wallet.domain.wallet.WalletId;
import com.logtari.paynest.wallet.infrastructure.persistence.entity.WalletTransactionEntity;
import org.springframework.stereotype.Component;

@Component
public class WalletTransactionMapper {
    public WalletTransactionEntity toEntity(WalletTransaction transaction) {

        return new WalletTransactionEntity(
                transaction.getId(),
                transaction.getWalletId().walletId(),
                transaction.getType(),
                transaction.getAmount().amount(),
                transaction.getAmount().currency().name(),
                transaction.getCreatedAt()
        );
    }

    public WalletTransaction toDomain(WalletTransactionEntity entity) {

        return new WalletTransaction(
                entity.getId(),
                new WalletId(entity.getWalletId()),
                entity.getType(),
                new Money(entity.getAmount(), Currency.valueOf(entity.getCurrency())),
                entity.getCreatedAt()
        );
    }
}
