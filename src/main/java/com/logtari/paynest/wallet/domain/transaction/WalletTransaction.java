package com.logtari.paynest.wallet.domain.transaction;

import com.logtari.paynest.wallet.domain.wallet.Money;
import com.logtari.paynest.wallet.domain.wallet.WalletId;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.util.UUID;

import static com.logtari.paynest.wallet.domain.transaction.TransactionType.WITHRAWAL;

@Getter
@RequiredArgsConstructor
public final class WalletTransaction {
    private final UUID id;
    private final WalletId walletId;
    private final TransactionType type;
    private final Money amount;
    private final Instant createdAt;

    public static WalletTransaction withdrawa(WalletId walletId, Money amount){
        return new WalletTransaction(
                UUID.randomUUID(),
                walletId,
                WITHRAWAL,
                amount,
                Instant.now()
        );
    }

}
