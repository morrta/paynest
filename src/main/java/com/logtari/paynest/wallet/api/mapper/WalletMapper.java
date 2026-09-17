package com.logtari.paynest.wallet.api.mapper;

import com.logtari.paynest.wallet.api.response.WalletResponse;
import com.logtari.paynest.wallet.domain.Wallet;


public final class WalletMapper {
    public static WalletResponse from(Wallet wallet) {
        return WalletResponse.builder()
                .walletId(wallet.getWalletId().walletId().toString())
                .customerId(wallet.getOwnerId().customerId().toString())
                .balance(wallet.getBalance().amount().toPlainString())
                .currency(wallet.getBalance().currency().name())
                .build();
    }
}
