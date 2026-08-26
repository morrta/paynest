package com.logtari.paynest.wallet;

import java.util.Objects;
import java.util.UUID;

public record CustomerId(UUID customerId) {
    public CustomerId {
        Objects.requireNonNull(customerId,"Customer Id must not be null");
    }
    public static CustomerId generate(){
        return new CustomerId(UUID.randomUUID());
    }
}
