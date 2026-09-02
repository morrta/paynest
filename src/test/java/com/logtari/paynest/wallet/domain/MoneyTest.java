package com.logtari.paynest.wallet.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class MoneyTest {

    @Test
    void shouldCreateMoneyInEuros() {
        Money money = Money.euros(new BigDecimal("100.00"));

        assertThat(money.getAmount()).isEqualByComparingTo("100.00");
        assertThat(money.getCurrency()).isEqualTo(Currency.EUR);
    }

    @Test
    void shouldCreateMoneyInUSD(){
        Money money = Money.dollars(new BigDecimal("100.00"));
        assertThat(money.getAmount()).isEqualByComparingTo("100.00");
        assertThat(money.getCurrency()).isEqualTo(Currency.USD);
    }

    @Test
    void shouldAllowZeroMoney() {
        Money money = Money.euros(BigDecimal.ZERO);

        assertThat(money.getAmount())
                .isEqualByComparingTo("0");
    }

    @Test
    void shouldRejectNegativeMoney() {
        assertThatThrownBy(() ->
                Money.euros(new BigDecimal("-10"))
        )
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Money amount cannot be negative");
    }

    @Test
    void shouldAddMoney() {
        Money first = Money.euros(new BigDecimal("100"));
        Money second = Money.euros(new BigDecimal("50"));

        Money result = first.add(second);

        assertThat(result)
                .isEqualTo(Money.euros(new BigDecimal("150")));
    }

    @Test
    void shouldSubtractMoney() {
        Money first = Money.euros(new BigDecimal("100"));
        Money second = Money.euros(new BigDecimal("30"));

        Money result = first.subtract(second);

        assertThat(result)
                .isEqualTo(Money.euros(new BigDecimal("70")));
    }

    @Test
    void shouldRejectSubtractionResultingInNegativeMoney() {
        Money first = Money.euros(new BigDecimal("30"));
        Money second = Money.euros(new BigDecimal("50"));

        assertThatThrownBy(() -> first.subtract(second))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldRejectAddingDifferentCurrencies() {
        Money eur = new Money(
                new BigDecimal("100"),
                Currency.EUR
        );
        Money usd = new Money(
                new BigDecimal("50"),
                Currency.USD
        );
        assertThatThrownBy(() -> eur.add(usd))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Cannot operate with different currencies");
    }
}
