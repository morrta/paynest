package com.logtari.paynest.wallet.application;

import com.logtari.paynest.wallet.application.repository.WalletRepository;
import com.logtari.paynest.wallet.application.service.CreateWalletService;
import com.logtari.paynest.wallet.domain.Customer;
import com.logtari.paynest.wallet.domain.CustomerId;
import com.logtari.paynest.wallet.domain.Wallet;
import com.logtari.paynest.wallet.infrastructure.InMemoryWalletRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class CreateWalletServiceTest {
    private WalletRepository repository;
    private CreateWalletService service;

    @BeforeEach
    void setUp() {
        repository = new InMemoryWalletRepository();
        service = new CreateWalletService(repository);
    }

    @Test
    void shouldCreateWallet() {

        Customer customer = new Customer(
                CustomerId.generate(),
                "Alice"
        );

        Wallet wallet = service.execute(customer);

        assertThat(wallet.getOwnerId())
                .isEqualTo(customer.customerId());

        assertThat(repository.findById(wallet.getWalletId()))
                .contains(wallet);
    }
}
