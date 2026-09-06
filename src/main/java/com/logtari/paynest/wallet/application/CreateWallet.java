package com.logtari.paynest.wallet.application;

import com.logtari.paynest.wallet.domain.Customer;
import com.logtari.paynest.wallet.domain.Wallet;

public interface CreateWallet {
    Wallet execute(Customer customer);
}
