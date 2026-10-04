CREATE TABLE wallet_transactions (
                                     id UUID PRIMARY KEY,
                                     wallet_id UUID NOT NULL,
                                     type VARCHAR(20) NOT NULL,
                                     amount NUMERIC(19, 2) NOT NULL,
                                     currency VARCHAR(3) NOT NULL,
                                     created_at TIMESTAMP WITH TIME ZONE NOT NULL,

                                     CONSTRAINT fk_transaction_wallet
                                         FOREIGN KEY (wallet_id)
                                             REFERENCES wallets(id)
                                     ON DELETE CASCADE
);

CREATE INDEX idx_wallet_transactions_wallet_created
    ON wallet_transactions(wallet_id, created_at DESC);