package com.logtari.paynest.wallet.api.mapper;

import com.logtari.paynest.wallet.api.response.WalletResponse;
import com.logtari.paynest.wallet.domain.Wallet;


public final class WalletMapper {
    public static WalletResponse from(Wallet wallet) {
        return WalletResponse.builder()
                .walletId(wallet.getWalletId().toString())
                .customerId(wallet.getOwnerId().toString())
                .balance(wallet.getBalance().toString())
                .currency(wallet.getBalance().currency().name())
                .build();
    }
}
