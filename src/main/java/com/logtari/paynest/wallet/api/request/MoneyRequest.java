package com.logtari.paynest.wallet.api.request;

import jakarta.validation.constraints.NotBlank;

public record MoneyRequest(
        @NotBlank
        String amount,

        @NotBlank
        String currency
) {
}
