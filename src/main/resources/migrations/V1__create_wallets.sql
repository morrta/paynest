CREATE TABLE wallets (
                         id UUID PRIMARY KEY,
                         balance NUMERIC(19, 2) NOT NULL,
                         currency VARCHAR(3) NOT NULL,
                         created_at TIMESTAMP WITH TIME ZONE NOT NULL,
                         updated_at TIMESTAMP WITH TIME ZONE NOT NULL
);