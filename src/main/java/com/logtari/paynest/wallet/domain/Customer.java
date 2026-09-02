package com.logtari.paynest.wallet.domain;

import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

public record Customer(CustomerId customerId, String name) {
    public Customer(CustomerId customerId, String name) {
        this.customerId = Objects.requireNonNull(customerId, "Customer ID must not be null");
        if (StringUtils.isBlank(name)) {
            throw new IllegalArgumentException("Customer name must not be Blank");
        }
        this.name = name;
    }
}
