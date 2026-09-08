package com.logtari.paynest.wallet.domain;

import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

public record Customer(CustomerId customerId, String customerName) {
    public Customer(CustomerId customerId, String customerName) {
        this.customerId = Objects.requireNonNull(customerId, "Customer ID must not be null");
        if (StringUtils.isBlank(customerName)) {
            throw new IllegalArgumentException("Customer customerName must not be Blank");
        }
        this.customerName = customerName;
    }
}
