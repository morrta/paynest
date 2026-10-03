package com.logtari.paynest.wallet.application;

import com.logtari.paynest.wallet.domain.wallet.Wallet;
import com.logtari.paynest.wallet.domain.wallet.WalletId;

public interface GetWallet {
    Wallet execute(WalletId walletId);
}
