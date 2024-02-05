package uk.gov.pay.payments.tokens.resource;

import com.fasterxml.jackson.annotation.JsonInclude;
import uk.gov.pay.payments.payments.resource.PaymentResponse;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record TokenResponse(
        PaymentResponse payment,
        boolean used
) {
}
