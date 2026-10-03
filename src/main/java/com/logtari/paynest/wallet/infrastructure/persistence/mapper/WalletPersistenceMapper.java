package com.logtari.paynest.wallet.infrastructure.persistence.mapper;

import com.logtari.paynest.wallet.domain.customer.CustomerId;
import com.logtari.paynest.wallet.domain.transaction.Currency;
import com.logtari.paynest.wallet.domain.transaction.Money;
import com.logtari.paynest.wallet.domain.wallet.Wallet;
import com.logtari.paynest.wallet.domain.wallet.WalletId;
import com.logtari.paynest.wallet.infrastructure.persistence.entity.WalletEntity;
import org.springframework.stereotype.Component;

@Component
public class WalletPersistenceMapper {
    public WalletEntity toEntity(Wallet wallet) {

        return WalletEntity.builder()
                .id(wallet.getWalletId().walletId())
                .customerId(wallet.getOwnerId().customerId())
                .balance(wallet.getBalance().amount())
                .currency(wallet.getBalance().currency().name())
                .build();
    }

    public Wallet toDomain(WalletEntity entity) {

        return Wallet.reconstitute(
                new WalletId(entity.getId()),
                new CustomerId(entity.getCustomerId()),
                new Money(entity.getBalance(), Currency.valueOf(entity.getCurrency()))
        );
    }
}
