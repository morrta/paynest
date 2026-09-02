package com.logtari.paynest.wallet.domain;

import java.math.BigDecimal;
import java.util.Objects;

public record Money(BigDecimal amount, Currency currency) {
    public Money(BigDecimal amount, Currency currency) {
        this.amount = Objects.requireNonNull(amount, "Money amount must not be null ");
        this.currency = Objects.requireNonNull(currency, "Money currency must not be null");

        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Money amount cannot be negative");
        }
    }

    public static Money euros(BigDecimal amount) {
        return new Money(amount, Currency.EUR);
    }

    public static Money dollars(BigDecimal amount) {
        return new Money(amount, Currency.USD);
    }

    public Money add(Money moneyToAdd) {
        requireSameCurrency(moneyToAdd);
        return new Money(amount.add(moneyToAdd.amount()), currency);
    }

    public Money subtract(Money moneyToSubstract) {
        requireSameCurrency(moneyToSubstract);
        // we do not check for negative resulting balance here, this is the wallet responsibility.
        return new Money(amount.subtract(moneyToSubstract.amount), currency);
    }

    void requireSameCurrency(Money otherAmount) {
        Objects.requireNonNull(otherAmount, "Money amount cannot be null");
        if (currency != otherAmount.currency) {
            throw new IllegalArgumentException("Cannot operate with different currencies");
        }
    }
}
