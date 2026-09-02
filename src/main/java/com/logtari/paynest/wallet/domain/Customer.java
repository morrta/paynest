package com.logtari.paynest.wallet.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

@Getter
@EqualsAndHashCode
public class Customer {
    private final CustomerId customerId;
    private final String name;

    public Customer(CustomerId customerId, String name) {
        this.customerId = Objects.requireNonNull(customerId, "Customer ID must not be null");
        if(StringUtils.isBlank(name)){
            throw new IllegalArgumentException("Customer name must not be Blank");
        }
        this.name = name;
    }
}
