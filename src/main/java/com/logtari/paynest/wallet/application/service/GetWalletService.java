package com.logtari.paynest.wallet.application.service;

import com.logtari.paynest.wallet.application.GetWallet;
import com.logtari.paynest.wallet.application.repository.WalletRepository;
import com.logtari.paynest.wallet.domain.Wallet;
import com.logtari.paynest.wallet.domain.WalletId;
import com.logtari.paynest.wallet.domain.exceptions.WalletNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class GetWalletService implements GetWallet {
    private final WalletRepository walletRepository;

    public GetWalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    @Override
    public Wallet execute(WalletId walletId) {
        return walletRepository.findById(walletId).orElseThrow(() -> new WalletNotFoundException(walletId));
    }
}
