package uk.gov.pay.payments.app;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Environment;
import org.hibernate.SessionFactory;
import uk.gov.pay.payments.util.IdGenerator;

import javax.inject.Singleton;
import java.time.InstantSource;

public class PaymentsModule extends AbstractModule {
    private final PaymentsConfig configuration;
    private final Environment environment;
    private final HibernateBundle<PaymentsConfig> hibernate;

    public PaymentsModule(final PaymentsConfig configuration, final Environment environment, HibernateBundle<PaymentsConfig> hibernate) {
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

    @Provides
    @Singleton
    public InstantSource instantSource() {
        return InstantSource.system();
    }

    @Provides
    @Singleton
    public IdGenerator externalIdGenerator() {
        return new IdGenerator();
    }
}
