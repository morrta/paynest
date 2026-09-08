package com.logtari.paynest.wallet.api.request;

import jakarta.validation.constraints.NotBlank;

public record CreateWalletRequest(
        @NotBlank
        String customerId,

        @NotBlank
        String customerName
) {
}
