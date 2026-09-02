package com.logtari.paynest.wallet.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class CustomerTest {
    @Test
    void shouldCreateCustomer() {
        CustomerId id = CustomerId.generate();

        Customer customer = new Customer(id, "Mohamed");

        assertThat(customer.getCustomerId()).isEqualTo(id);
        assertThat(customer.getName()).isEqualTo("Mohamed");
    }

    @Test
    void shouldRejectBlankName() {
        assertThatThrownBy(() ->
                new Customer(
                        CustomerId.generate(),
                        " "
                )
        )
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldRejectNullName() {
        assertThatThrownBy(() ->
                new Customer(
                        CustomerId.generate(),
                        null
                )
        )
                .isInstanceOf(IllegalArgumentException.class);
    }
}
