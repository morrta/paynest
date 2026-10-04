package com.logtari.paynest.wallet.domain;

import com.logtari.paynest.wallet.domain.transaction.TransactionType;
import com.logtari.paynest.wallet.domain.transaction.WalletTransaction;
import com.logtari.paynest.wallet.domain.wallet.Money;
import com.logtari.paynest.wallet.domain.wallet.WalletId;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

public class WalletTransactionTest {
    @Test
    void shouldCreateDepositTransaction() {
        WalletId walletId = WalletId.generate();
        Money amount = Money.euros(new BigDecimal("100"));
        Instant before = Instant.now();

        WalletTransaction transaction = WalletTransaction.deposit(walletId, amount);

        assertThat(transaction.getId()).isNotNull();
        assertThat(transaction.getType()).isEqualTo(TransactionType.DEPOSIT);
        assertThat(transaction.getWalletId()).isEqualTo(walletId);
        assertThat(transaction.getAmount()).isEqualTo(amount);
        assertThat(transaction.getCreatedAt()).isBetween(before, Instant.now());
    }

    @Test
    void shouldCreateWithdrawalTransaction() {
        WalletId walletId = WalletId.generate();
        Money amount = Money.euros(new BigDecimal("30"));
        Instant before = Instant.now();

        WalletTransaction transaction = WalletTransaction.withdrawal(walletId, amount);

        assertThat(transaction.getId()).isNotNull();
        assertThat(transaction.getType()).isEqualTo(TransactionType.WITHRAWAL);
        assertThat(transaction.getWalletId()).isEqualTo(walletId);
        assertThat(transaction.getAmount()).isEqualTo(amount);
        assertThat(transaction.getCreatedAt()).isBetween(before, Instant.now());
    }

    @Test
    void shouldGenerateUniqueIdForEachTransaction() {
        WalletId walletId = WalletId.generate();
        Money amount = Money.euros(new BigDecimal("10"));

        WalletTransaction first = WalletTransaction.deposit(walletId, amount);
        WalletTransaction second = WalletTransaction.deposit(walletId, amount);

        assertThat(first.getId()).isNotEqualTo(second.getId());
    }
}
