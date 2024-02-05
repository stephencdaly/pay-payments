package uk.gov.pay.payments.payments.resource;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import uk.gov.pay.payments.app.LinksConfig;
import uk.gov.pay.payments.payments.dao.PaymentEntity;
import uk.gov.pay.payments.tokens.dao.TokenEntity;
import uk.gov.service.payments.commons.api.json.ApiResponseInstantSerializer;

import javax.validation.constraints.NotEmpty;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.time.Instant;

public record PaymentResponse (
        @JsonProperty("external_id") String externalId,
        @JsonProperty("gateway_account_id") @NotEmpty long gatewayAccountId,
        @JsonProperty("amount") @NotEmpty long amount,
        @JsonProperty("reference") @NotEmpty String reference,
        @JsonProperty("description") @NotEmpty String description,
        @JsonProperty("return_url") String returnUrl,
        @JsonProperty("delayed_capture") boolean delayedCapture,
        @JsonProperty("created_date") @JsonSerialize(using = ApiResponseInstantSerializer.class) Instant createdDate,
        @JsonProperty("links") PaymentLinks links
) {
    
    public static PaymentResponse from(PaymentEntity payment, TokenEntity token, LinksConfig linksConfig) {
        var links = new PaymentLinks(
                new Link(nextUrl(token.getToken(), linksConfig).toString(), "GET")
        );
        return new PaymentResponse(
                payment.getExternalId(),
                payment.getGatewayAccountId(),
                payment.getAmount(),
                payment.getReference(),
                payment.getDescription(),
                payment.getReturnUrl(),
                payment.isDelayedCapture(), 
                payment.getCreatedDate(),
                links
        );
    }

    private static URI nextUrl(String token, LinksConfig linksConfig) {
        return UriBuilder.fromUri(linksConfig.getFrontendUrl())
                .path("secure")
                .path(token)
                .build();
    }
}
