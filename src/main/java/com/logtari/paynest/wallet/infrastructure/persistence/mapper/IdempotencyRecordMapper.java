package com.logtari.paynest.wallet.infrastructure.persistence.mapper;

import com.logtari.paynest.wallet.domain.idempotency.IdempotencyRecord;
import com.logtari.paynest.wallet.domain.wallet.WalletId;
import com.logtari.paynest.wallet.infrastructure.persistence.entity.IdempotencyRecordEntity;
import org.springframework.stereotype.Component;

@Component
public class IdempotencyRecordMapper {
    public IdempotencyRecordEntity toEntity(IdempotencyRecord record) {

        return new IdempotencyRecordEntity(
                record.getId(),
                record.getWalletId().walletId(),
                record.getIdempotencyKey(),
                record.getRequestHash(),
                record.getResponseStatus(),
                record.getResponseBody(),
                record.getCreatedAt()
        );
    }

    public IdempotencyRecord toDomain(IdempotencyRecordEntity entity) {

        return new IdempotencyRecord(
                entity.getId(),
                new WalletId(entity.getWalletId()),
                entity.getIdempotencyKey(),
                entity.getRequestHash(),
                entity.getResponseStatus(),
                entity.getResponseBody(),
                entity.getCreatedAt()
        );
    }
}
