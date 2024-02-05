--liquibase formatted sql

--changeset uk.gov.pay:create-table-webhooks

CREATE table tokens (
    id SERIAL PRIMARY KEY,
    create_date TIMESTAMP WITH TIME ZONE NOT NULL,
    external_id VARCHAR(26) NOT NULL,
    gateway_account_id BIGINT NOT NULL,
    amount BIGINT NOT NULL,
    reference VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    return_url TEXT,
    delayed_capture BOOLEAN,
    moto BOOLEAN
)

--rollback DROP table payments
