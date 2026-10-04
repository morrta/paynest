package com.logtari.paynest.wallet.application;

import com.logtari.paynest.wallet.application.repository.WalletRepository;
import com.logtari.paynest.wallet.application.service.DepositMoneyService;
import com.logtari.paynest.wallet.domain.customer.CustomerId;
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

public class DepositMoneyServiceTest {
    private WalletRepository walletRepository;
    private InMemoryWalletTransactionRepository transactionRepository;
    private DepositMoneyService service;

    @BeforeEach
    void setUp() {
        walletRepository = new InMemoryWalletRepository();
        transactionRepository = new InMemoryWalletTransactionRepository();
        service = new DepositMoneyService(walletRepository, transactionRepository);
    }

    @Test
    void shouldIncreaseBalance() {
        Wallet wallet = walletRepository.save(new Wallet(WalletId.generate(), CustomerId.generate()));

        Wallet updated = service.execute(wallet.getWalletId(), Money.euros(new BigDecimal("100")));

        assertThat(updated.getBalance())
                .isEqualTo(Money.euros(new BigDecimal("100")));
    }

    @Test
    void shouldRecordDepositTransaction() {
        Wallet wallet = walletRepository.save(new Wallet(WalletId.generate(), CustomerId.generate()));
        Money amount = Money.euros(new BigDecimal("100"));

        service.execute(wallet.getWalletId(), amount);

        assertThat(transactionRepository.findAll()).hasSize(1);
        WalletTransaction transaction = transactionRepository.findAll().getFirst();
        assertThat(transaction.getType()).isEqualTo(TransactionType.DEPOSIT);
        assertThat(transaction.getWalletId()).isEqualTo(wallet.getWalletId());
        assertThat(transaction.getAmount()).isEqualTo(amount);
    }

    @Test
    void shouldRecordOneTransactionPerDeposit() {
        Wallet wallet = walletRepository.save(new Wallet(WalletId.generate(), CustomerId.generate()));

        service.execute(wallet.getWalletId(), Money.euros(new BigDecimal("100")));
        service.execute(wallet.getWalletId(), Money.euros(new BigDecimal("50")));

        assertThat(transactionRepository.findAll())
                .extracting(WalletTransaction::getAmount)
                .containsExactly(
                        Money.euros(new BigDecimal("100")),
                        Money.euros(new BigDecimal("50"))
                );
    }

    @Test
    void shouldNotRecordTransactionWhenWalletDoesNotExist() {
        WalletId unknownWallet = WalletId.generate();

        assertThatThrownBy(() -> service.execute(unknownWallet, Money.euros(new BigDecimal("100"))))
                .isInstanceOf(WalletNotFoundException.class);

        assertThat(transactionRepository.findAll()).isEmpty();
    }
}
