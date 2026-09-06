package com.logtari.paynest.wallet.application.service;

import com.logtari.paynest.wallet.application.repository.WalletRepository;
import com.logtari.paynest.wallet.domain.Money;
import com.logtari.paynest.wallet.domain.Wallet;
import com.logtari.paynest.wallet.domain.WalletId;
import com.logtari.paynest.wallet.domain.exceptions.WalletNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WithdrawMoneyService {
    private final WalletRepository walletRepository;

    public WithdrawMoneyService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    public Wallet execute(WalletId walletId, Money amount) {
        Wallet wallet = walletRepository.findById(walletId).orElseThrow(() -> new WalletNotFoundException(walletId));
        wallet.withdraw(amount);
        return walletRepository.save(wallet);
    }
}
