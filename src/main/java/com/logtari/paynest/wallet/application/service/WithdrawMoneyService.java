package com.logtari.paynest.wallet.application.service;

import com.logtari.paynest.wallet.application.repository.WalletRepository;
import com.logtari.paynest.wallet.application.repository.WalletTransactionRepository;
import com.logtari.paynest.wallet.domain.transaction.WalletTransaction;
import com.logtari.paynest.wallet.domain.wallet.Money;
import com.logtari.paynest.wallet.domain.wallet.Wallet;
import com.logtari.paynest.wallet.domain.wallet.WalletId;
import com.logtari.paynest.wallet.domain.exceptions.WalletNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WithdrawMoneyService {
    private final WalletRepository walletRepository;
    private final WalletTransactionRepository walletTransactionRepository;

    @Transactional
    public Wallet execute(WalletId walletId, Money amount) {
        Wallet wallet = walletRepository.findById(walletId).orElseThrow(() -> new WalletNotFoundException(walletId));
        wallet.withdraw(amount);
        Wallet saved = walletRepository.save(wallet);
        walletTransactionRepository.save(WalletTransaction.withdrawal(walletId,amount));
        return saved;
    }
}
