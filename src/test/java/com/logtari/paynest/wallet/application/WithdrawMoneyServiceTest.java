package com.logtari.paynest.wallet.application;

import com.logtari.paynest.wallet.application.repository.WalletRepository;
import com.logtari.paynest.wallet.application.service.WithdrawMoneyService;
import com.logtari.paynest.wallet.domain.customer.CustomerId;
import com.logtari.paynest.wallet.domain.exceptions.InsufficientFundsException;
import com.logtari.paynest.wallet.domain.exceptions.WalletNotFoundException;
import com.logtari.paynest.wallet.domain.transaction.TransactionType;
import com.logtari.paynest.wallet.domain.transaction.WalletTransaction;
import com.logtari.paynest.wallet.domain.wallet.Money;
import com.logtari.paynest.wallet.domain.wallet.Wallet;
import com.logtari.paynest.wallet.domain.wallet.WalletId;
import com.logtari.paynest.wallet.infrastructure.persistence.InMemoryWalletRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class WithdrawMoneyServiceTest {
    private WalletRepository walletRepository;
    private InMemoryWalletTransactionRepository transactionRepository;
    private WithdrawMoneyService service;

    @BeforeEach
    void setUp() {
        walletRepository = new InMemoryWalletRepository();
        transactionRepository = new InMemoryWalletTransactionRepository();
        service = new WithdrawMoneyService(walletRepository, transactionRepository);
    }

    private Wallet walletWithBalance(String amount) {
        Wallet wallet = new Wallet(WalletId.generate(), CustomerId.generate());
        wallet.deposit(Money.euros(new BigDecimal(amount)));
        return walletRepository.save(wallet);
    }

    @Test
    void shouldDecreaseBalance() {
        Wallet wallet = walletWithBalance("100");

        Wallet updated = service.execute(wallet.getWalletId(), Money.euros(new BigDecimal("30")));

        assertThat(updated.getBalance())
                .isEqualTo(Money.euros(new BigDecimal("70")));
    }

    @Test
    void shouldRecordWithdrawalTransaction() {
        Wallet wallet = walletWithBalance("100");
        Money amount = Money.euros(new BigDecimal("30"));

        service.execute(wallet.getWalletId(), amount);

        assertThat(transactionRepository.findAll()).hasSize(1);
        WalletTransaction transaction = transactionRepository.findAll().getFirst();
        assertThat(transaction.getType()).isEqualTo(TransactionType.WITHRAWAL);
        assertThat(transaction.getWalletId()).isEqualTo(wallet.getWalletId());
        assertThat(transaction.getAmount()).isEqualTo(amount);
    }

    @Test
    void shouldNotRecordTransactionWhenFundsAreInsufficient() {
        Wallet wallet = walletWithBalance("10");

        assertThatThrownBy(() -> service.execute(wallet.getWalletId(), Money.euros(new BigDecimal("30"))))
                .isInstanceOf(InsufficientFundsException.class);

        assertThat(transactionRepository.findAll()).isEmpty();
    }

    @Test
    void shouldNotRecordTransactionWhenWalletDoesNotExist() {
        WalletId unknownWallet = WalletId.generate();

        assertThatThrownBy(() -> service.execute(unknownWallet, Money.euros(new BigDecimal("30"))))
                .isInstanceOf(WalletNotFoundException.class);

        assertThat(transactionRepository.findAll()).isEmpty();
    }
}
