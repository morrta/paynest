package com.logtari.paynest.wallet.application;

import com.logtari.paynest.TestcontainersConfiguration;
import com.logtari.paynest.wallet.application.repository.WalletRepository;
import com.logtari.paynest.wallet.application.repository.WalletTransactionRepository;
import com.logtari.paynest.wallet.application.service.DepositMoneyService;
import com.logtari.paynest.wallet.application.service.WithdrawMoneyService;
import com.logtari.paynest.wallet.domain.customer.CustomerId;
import com.logtari.paynest.wallet.domain.wallet.Currency;
import com.logtari.paynest.wallet.domain.wallet.Money;
import com.logtari.paynest.wallet.domain.wallet.Wallet;
import com.logtari.paynest.wallet.domain.wallet.WalletId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@Import(TestcontainersConfiguration.class)
class MoneyOperationsServiceIT {

    @Autowired
    private WithdrawMoneyService withdrawMoneyService;

    @Autowired
    private DepositMoneyService depositMoneyService;

    @Autowired
    private WalletRepository walletRepository;

    @MockitoBean
    private WalletTransactionRepository walletTransactionRepository;

    private final RuntimeException failure = new RuntimeException("DB unavailable");

    @BeforeEach
    void failTransactionRecording() {
        when(walletTransactionRepository.save(any())).thenThrow(failure);
    }

    private WalletId walletWithBalance(String amount) {
        WalletId walletId = WalletId.generate();
        Wallet wallet = Wallet.open(walletId, CustomerId.generate(), Currency.EUR);
        wallet.deposit(Money.euros(new BigDecimal(amount)));
        walletRepository.save(wallet);
        return walletId;
    }

    private BigDecimal balanceOf(WalletId walletId) {
        return walletRepository.findById(walletId).orElseThrow().getBalance().amount();
    }

    @Test
    void withdrawal_should_not_change_balance_if_transaction_recording_fails() {
        WalletId walletId = walletWithBalance("100.00");

        assertThatThrownBy(() -> withdrawMoneyService.execute(walletId, Money.euros(new BigDecimal("40.00"))))
                .isSameAs(failure);

        assertThat(balanceOf(walletId)).isEqualByComparingTo("100.00");
    }

    @Test
    void deposit_should_not_change_balance_if_transaction_recording_fails() {
        WalletId walletId = walletWithBalance("100.00");

        assertThatThrownBy(() -> depositMoneyService.execute(walletId, Money.euros(new BigDecimal("40.00"))))
                .isSameAs(failure);

        assertThat(balanceOf(walletId)).isEqualByComparingTo("100.00");
    }
}
