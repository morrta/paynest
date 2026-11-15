package com.logtari.paynest.wallet.domain.idempotency;

import com.logtari.paynest.wallet.domain.wallet.WalletId;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Getter
@RequiredArgsConstructor
public final class IdempotencyRecord {
    private final UUID id;
    private final WalletId walletId;
    private final String idempotencyKey;
    private final String requestHash;
    private final Integer responseStatus;
    private final String responseBody;
    private final Instant createdAt;
}
