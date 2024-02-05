package uk.gov.pay.payments.app;

import com.google.inject.AbstractModule;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Environment;
import org.hibernate.SessionFactory;

public class PayemntsModule extends AbstractModule {
    private final PaymentsConfig configuration;
    private final Environment environment;
    private final HibernateBundle<PaymentsConfig> hibernate;

    public PayemntsModule(final PaymentsConfig configuration, final Environment environment, HibernateBundle<PaymentsConfig> hibernate) {
        this.configuration = configuration;
        this.environment = environment;
        this.hibernate = hibernate;
    }

    @Override
    protected void configure() {
        bind(PaymentsConfig.class).toInstance(configuration);
        bind(Environment.class).toInstance(environment);
        bind(SessionFactory.class).toInstance(hibernate.getSessionFactory());
    }
}
