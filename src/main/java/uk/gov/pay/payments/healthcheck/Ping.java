package uk.gov.pay.payments.healthcheck;

import com.codahale.metrics.health.HealthCheck;

public class Ping extends HealthCheck {
    @Override
    protected Result check()  {
        return Result.healthy();
    }
}
