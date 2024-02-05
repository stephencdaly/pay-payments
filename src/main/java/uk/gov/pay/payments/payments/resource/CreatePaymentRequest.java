package uk.gov.pay.payments.payments.resource;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;

public record CreatePaymentRequest (
    @JsonProperty("gateway_account_id") long gatewayAccountId,
    @JsonProperty("amount") long amount,
    @JsonProperty("reference") @NotEmpty String reference,
    @JsonProperty("description") @NotEmpty String description,
    @JsonProperty("return_url") String returnUrl,
    @JsonProperty("delayed_capture") boolean delayedCapture
) {}
