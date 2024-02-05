package uk.gov.pay.payments.tokens.resource;

import uk.gov.pay.payments.payments.resource.PaymentResponse;

public record TokenResponse(
        PaymentResponse payment,
        boolean used
) {
}
