package com.logtari.paynest.wallet.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Objects;

@Getter
@EqualsAndHashCode
public final class Wallet {
    private final WalletId walletId;
    private final CustomerId ownerId;
    private Money balance;

    public Wallet(WalletId walletId, CustomerId ownerId) {
        this.walletId = Objects.requireNonNull(walletId, "Wallet ID must not be null");
        this.ownerId = Objects.requireNonNull(ownerId, "Owner ID must not be null");

        this.balance = Money.euros(BigDecimal.ZERO);
    }

    public void deposit(Money amountToDeposit){
        Objects.requireNonNull(amountToDeposit, "Deposit amountToDeposit must not be null");
        balance = balance.add(amountToDeposit);
    }

    public void withdraw(Money amountToWithdraw){
        Objects.requireNonNull(amountToWithdraw, "withdrawal amount must not be null");
        balance = balance.subtract(amountToWithdraw);
    }
}
