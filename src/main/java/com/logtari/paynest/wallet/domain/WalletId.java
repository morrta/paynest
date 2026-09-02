package com.logtari.paynest.wallet.domain;

import java.util.Objects;
import java.util.UUID;

public record WalletId(UUID walletId) {
    public WalletId {
        Objects.requireNonNull(walletId, "Wallet Id must not be null");
    }
    public static WalletId generate(){
        return new WalletId(UUID.randomUUID());
    }
}
