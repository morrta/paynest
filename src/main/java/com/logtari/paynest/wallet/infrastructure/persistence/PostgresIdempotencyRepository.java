package com.logtari.paynest.wallet.infrastructure.persistence;

import com.logtari.paynest.wallet.application.repository.IdempotencyRepository;
import com.logtari.paynest.wallet.domain.idempotency.IdempotencyRecord;
import com.logtari.paynest.wallet.domain.wallet.WalletId;
import com.logtari.paynest.wallet.infrastructure.persistence.mapper.IdempotencyRecordMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PostgresIdempotencyRepository implements IdempotencyRepository {

    private final SpringDataIdempotencyRepository repository;
    private final IdempotencyRecordMapper mapper;

    @Override
    public IdempotencyRecord save(IdempotencyRecord record) {
        var entity = mapper.toEntity(record);
        var saved = repository.save(entity);

        return mapper.toDomain(saved);
    }

    @Override
    public Optional<IdempotencyRecord> findByWalletIdAndIdempotencyKey(WalletId walletId, String idempotencyKey) {
        return repository.findByWalletIdAndIdempotencyKey(walletId.walletId(), idempotencyKey)
                .map(mapper::toDomain);
    }
}
