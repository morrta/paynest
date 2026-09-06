package com.logtari.paynest.wallet.application;

import com.logtari.paynest.wallet.domain.Wallet;
import com.logtari.paynest.wallet.domain.WalletId;

public interface GetWallet {
    Wallet execute(WalletId walletId);
}
