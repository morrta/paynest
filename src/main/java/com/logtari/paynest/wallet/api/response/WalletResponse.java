package com.logtari.paynest.wallet.api.response;

import lombok.Builder;

@Builder
public record WalletResponse(
        String walletId,
        String customerId,
        String balance,
        String currency
) {
}
