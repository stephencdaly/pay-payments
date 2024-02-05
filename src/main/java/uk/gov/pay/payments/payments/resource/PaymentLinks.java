package uk.gov.pay.payments.payments.resource;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "PaymentLinks", description = "links for payment")
public record PaymentLinks(
        @JsonProperty("next_url") Link nextUrl
) {
}
