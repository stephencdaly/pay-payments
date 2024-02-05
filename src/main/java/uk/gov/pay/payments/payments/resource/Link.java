package uk.gov.pay.payments.payments.resource;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Link(
        @JsonProperty("href") String href,
        @JsonProperty("method") String method
) {
}
