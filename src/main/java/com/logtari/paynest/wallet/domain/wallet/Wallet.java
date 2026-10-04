package com.logtari.paynest.wallet.domain.wallet;

import com.logtari.paynest.wallet.domain.customer.CustomerId;
import com.logtari.paynest.wallet.domain.exceptions.InsufficientFundsException;
import lombok.Builder;
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

    @Builder
    public Wallet(WalletId walletId, CustomerId ownerId) {
        this(walletId, ownerId, Money.euros(BigDecimal.ZERO));
    }

    private Wallet(WalletId walletId, CustomerId ownerId, Money balance) {
        this.walletId = Objects.requireNonNull(walletId, "Wallet ID must not be null");
        this.ownerId = Objects.requireNonNull(ownerId, "Owner ID must not be null");
        this.balance = Objects.requireNonNull(balance, "Balance must not be null");
    }

    /**
     * Rebuilds an existing wallet (e.g. loaded from persistence) with its current balance.
     * New wallets should be created via the builder, which always starts at zero.
     */
    public static Wallet reconstitute(WalletId walletId, CustomerId ownerId, Money balance) {
        return new Wallet(walletId, ownerId, balance);
    }

    /**
     * Opens a brand-new wallet for the given owner with a zero balance in the given currency.
     */
    public static Wallet open(WalletId id, CustomerId ownerId, Currency currency) {
        Objects.requireNonNull(currency, "Currency must not be null");
        return new Wallet(id, ownerId, new Money(BigDecimal.ZERO, currency));
    }

    public void deposit(Money amountToDeposit){
        Objects.requireNonNull(amountToDeposit, "Deposit amountToDeposit must not be null");
        balance = balance.add(amountToDeposit);
    }

    public void withdraw(Money amountToWithdraw){
        Objects.requireNonNull(amountToWithdraw, "withdrawal amount must not be null");
       balance.requireSameCurrency(amountToWithdraw);
       if(amountToWithdraw.amount().compareTo(balance.amount()) > 0){
            throw new InsufficientFundsException();
       }
        balance = balance.subtract(amountToWithdraw);
    }
}
