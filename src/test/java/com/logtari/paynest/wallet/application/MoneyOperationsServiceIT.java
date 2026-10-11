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
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

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

    private void failTransactionRecording() {
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
        failTransactionRecording();

        assertThatThrownBy(() -> withdrawMoneyService.execute(walletId, Money.euros(new BigDecimal("40.00"))))
                .isSameAs(failure);

        assertThat(balanceOf(walletId)).isEqualByComparingTo("100.00");
    }

    @Test
    void deposit_should_not_change_balance_if_transaction_recording_fails() {
        WalletId walletId = walletWithBalance("100.00");
        failTransactionRecording();

        assertThatThrownBy(() -> depositMoneyService.execute(walletId, Money.euros(new BigDecimal("40.00"))))
                .isSameAs(failure);

        assertThat(balanceOf(walletId)).isEqualByComparingTo("100.00");
    }

    //Concurrency test:
    @Test
    void concurrent_withdrawals_should_not_corrupt_balance() throws Exception{
        WalletId walletId = walletWithBalance("100.00");

        CountDownLatch start = new CountDownLatch(1);
        int succeeded = 0;
        List<Throwable> failures = new ArrayList<>();

        // ExecutorService is AutoCloseable since Java 19: close() shuts it down and waits for the tasks
        try (ExecutorService executor = Executors.newFixedThreadPool(2)) {
            Future<Wallet> first = executor.submit(()->{
               start.await();
               return withdrawMoneyService.execute(walletId, Money.euros(new BigDecimal("80.00")));
            });
            Future<Wallet> second = executor.submit(()->{
               start.await();
                return withdrawMoneyService.execute(walletId, Money.euros(new BigDecimal("70.00")));
            });
            start.countDown();

            for (Future<Wallet> withdrawal : List.of(first, second)) {
                try {
                    withdrawal.get(10, TimeUnit.SECONDS);
                    succeeded++;
                } catch (ExecutionException e) {
                    failures.add(e.getCause());
                }
            }
        }

        // 80 + 70 > 100, so only one withdrawal may go through
        assertThat(succeeded).isEqualTo(1);
        assertThat(failures).singleElement().isInstanceOf(ObjectOptimisticLockingFailureException.class);
        assertThat(balanceOf(walletId)).satisfiesAnyOf(
                balance -> assertThat(balance).isEqualByComparingTo("20.00"),
                balance -> assertThat(balance).isEqualByComparingTo("30.00")
        );
    }
}
