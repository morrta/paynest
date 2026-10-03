package com.logtari.paynest.wallet.application;

import com.logtari.paynest.wallet.domain.customer.Customer;
import com.logtari.paynest.wallet.domain.wallet.Wallet;

public interface CreateWallet {
    Wallet execute(Customer customer);
}
