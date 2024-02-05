--liquibase formatted sql

--changeset uk.gov.pay:create-table-payments

CREATE table payments (
    id SERIAL PRIMARY KEY,
    created_date TIMESTAMP WITH TIME ZONE NOT NULL,
    external_id VARCHAR(26) NOT NULL,
    gateway_account_id BIGINT NOT NULL,
    amount BIGINT NOT NULL,
    reference VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    return_url TEXT,
    delayed_capture BOOLEAN NOT NULL,
    moto BOOLEAN NOT NULL
);

CREATE INDEX payments_external_id_idx ON payments(external_id);

--rollback DROP table payments
