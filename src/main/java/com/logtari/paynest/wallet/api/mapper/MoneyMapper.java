package com.logtari.paynest.wallet.api.mapper;

import com.logtari.paynest.wallet.api.request.MoneyRequest;
import com.logtari.paynest.wallet.domain.Currency;
import com.logtari.paynest.wallet.domain.Money;

public final class MoneyMapper {
    public static Money toMoney(MoneyRequest request) {

        Currency currency =
                Currency.valueOf(request.currency());

        return new Money(
                new java.math.BigDecimal(request.amount()),
                currency
        );
    }
}
