CREATE TABLE idempotency_records (
                                     id UUID PRIMARY KEY,
                                     wallet_id UUID NOT NULL,
                                     idempotency_key VARCHAR(255) NOT NULL,
                                     request_hash VARCHAR(64) NOT NULL,
                                     response_status INTEGER,
                                     response_body JSONB,
                                     created_at TIMESTAMPTZ NOT NULL DEFAULT now(),

                                     CONSTRAINT uq_idempotency_wallet_key
                                         UNIQUE (wallet_id, idempotency_key)
);