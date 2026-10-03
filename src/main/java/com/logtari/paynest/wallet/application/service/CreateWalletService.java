package com.logtari.paynest.wallet.application.service;

import com.logtari.paynest.wallet.application.CreateWallet;
import com.logtari.paynest.wallet.application.repository.WalletRepository;
import com.logtari.paynest.wallet.domain.customer.Customer;
import com.logtari.paynest.wallet.domain.wallet.Wallet;
import com.logtari.paynest.wallet.domain.wallet.WalletId;
import org.springframework.stereotype.Service;

@Service
public class CreateWalletService implements CreateWallet {

    private final WalletRepository walletRepository;

    public CreateWalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    @Override
    public Wallet execute(Customer customer) {
        Wallet wallet = Wallet.builder()
                .ownerId(customer.customerId())
                .walletId(WalletId.generate())
                .build();
        return walletRepository.save(wallet);
    }
}
