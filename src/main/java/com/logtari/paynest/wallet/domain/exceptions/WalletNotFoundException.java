package com.logtari.paynest.wallet.domain.exceptions;

import com.logtari.paynest.wallet.domain.wallet.WalletId;

public class WalletNotFoundException extends RuntimeException{
    public WalletNotFoundException(WalletId walletId) {
        super("Wallet not found:" + walletId);
    }
}
