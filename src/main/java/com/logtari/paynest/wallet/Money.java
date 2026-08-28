package com.logtari.paynest.wallet;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Objects;

@Getter
@EqualsAndHashCode
public final class Money {
    private final BigDecimal amount;
    private final Currency currency;

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
        return new Money(amount.add(moneyToAdd.getAmount()), currency);
    }

    public Money subtract(Money moneyToSubstract) {
        requireSameCurrency(moneyToSubstract);
        if (amount.compareTo(moneyToSubstract.amount) < 0) {
            throw new IllegalArgumentException("Resulting money from substaction cannot be negative");
        }
        return new Money(amount.subtract(moneyToSubstract.amount), currency);
    }

    private void requireSameCurrency(Money other) { //TODO: find a better name for this param
        Objects.requireNonNull(other, "Money amount cannot be null");
        if (currency != other.currency) {
            throw new IllegalArgumentException("Cannot operate with different currencies");
        }
    }
}
