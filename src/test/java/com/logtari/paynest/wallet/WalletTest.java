package com.logtari.paynest.wallet;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class WalletTest {
    @Test
    void shouldCreateWalletWithZeroBalance() {
        Wallet wallet = new Wallet(
                WalletId.generate(),
                CustomerId.generate()
        );

        assertThat(wallet.getBalance())
                .isEqualTo(Money.euros(BigDecimal.ZERO));
    }

    @Test
    void shouldAssociateWalletWithOwner() {
        CustomerId customerId = CustomerId.generate();

        Wallet wallet = new Wallet(
                WalletId.generate(),
                customerId
        );

        assertThat(wallet.getOwnerId())
                .isEqualTo(customerId);
    }

    @Test
    void shouldDepositMoney() {
        Wallet wallet = new Wallet(
                WalletId.generate(),
                CustomerId.generate()
        );

        wallet.deposit(Money.euros(new BigDecimal("100")));

        assertThat(wallet.getBalance())
                .isEqualTo(Money.euros(new BigDecimal("100")));
    }

    @Test
    void shouldAccumulateDeposits() {
        Wallet wallet = new Wallet(
                WalletId.generate(),
                CustomerId.generate()
        );

        wallet.deposit(Money.euros(new BigDecimal("100")));
        wallet.deposit(Money.euros(new BigDecimal("50")));

        assertThat(wallet.getBalance())
                .isEqualTo(Money.euros(new BigDecimal("150")));
    }

    @Test
    void shouldWithdrawMoney() {
        Wallet wallet = new Wallet(
                WalletId.generate(),
                CustomerId.generate()
        );

        wallet.deposit(Money.euros(new BigDecimal("100")));

        wallet.withdraw(
                Money.euros(new BigDecimal("40"))
        );

        assertThat(wallet.getBalance())
                .isEqualTo(Money.euros(new BigDecimal("60")));
    }

    @Test
    void shouldRejectWithdrawalWhenInsufficientFunds() {
        Wallet wallet = new Wallet(
                WalletId.generate(),
                CustomerId.generate()
        );

        wallet.deposit(
                Money.euros(new BigDecimal("100"))
        );

        assertThatThrownBy(() ->
                wallet.withdraw(
                        Money.euros(new BigDecimal("150"))
                )
        )
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldKeepBalanceUnchangedWhenWithdrawalFails() {
        Wallet wallet = new Wallet(
                WalletId.generate(),
                CustomerId.generate()
        );

        wallet.deposit(
                Money.euros(new BigDecimal("100"))
        );

        assertThatThrownBy(() ->
                wallet.withdraw(
                        Money.euros(new BigDecimal("150"))
                )
        );

        assertThat(wallet.getBalance()).isEqualTo(
                Money.euros(new BigDecimal("100"))
        );
    }
}
