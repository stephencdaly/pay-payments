--liquibase formatted sql

--changeset uk.gov.pay:create-table-tokens

CREATE table tokens (
    id SERIAL PRIMARY KEY,
    created_date TIMESTAMP WITH TIME ZONE NOT NULL,
    payment_id BIGINT NOT NULL,
    secure_redirect_token VARCHAR(255) NOT NULL,
    used BOOLEAN NOT NULL
)

CREATE INDEX tokens_secure_redirect_token_idx ON tokens(secure_redirect_token)

--rollback DROP table tokens
