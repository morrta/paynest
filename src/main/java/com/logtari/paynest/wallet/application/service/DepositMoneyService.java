package com.logtari.paynest.wallet.application.service;

import com.logtari.paynest.wallet.application.repository.WalletRepository;
import com.logtari.paynest.wallet.domain.Money;
import com.logtari.paynest.wallet.domain.Wallet;
import com.logtari.paynest.wallet.domain.WalletId;
import com.logtari.paynest.wallet.domain.exceptions.WalletNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class DepositMoneyService {
    private final WalletRepository walletRepository;

    public DepositMoneyService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    public Wallet execute(WalletId walletId, Money amount) {
        Wallet wallet = walletRepository.findById(walletId).orElseThrow(() -> new WalletNotFoundException(walletId));
        wallet.deposit(amount);
        return walletRepository.save(wallet);
    }
}
