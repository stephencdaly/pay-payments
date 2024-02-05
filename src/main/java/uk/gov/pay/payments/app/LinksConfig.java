package uk.gov.pay.payments.app;

import io.dropwizard.Configuration;

public class LinksConfig extends Configuration {
    private String frontendUrl;
    public String getFrontendUrl() {
        return frontendUrl;
    }
}
