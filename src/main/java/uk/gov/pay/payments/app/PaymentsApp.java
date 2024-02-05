package uk.gov.pay.payments.app;

import com.google.inject.Guice;
import com.google.inject.Injector;
import io.dropwizard.Application;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.dropwizard.DropwizardExports;
import io.prometheus.client.exporter.MetricsServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.pay.payments.healthcheck.HealthCheckResource;
import uk.gov.pay.payments.healthcheck.Ping;
import uk.gov.pay.payments.payments.dao.PaymentEntity;
import uk.gov.pay.payments.tokens.dao.TokenEntity;
import uk.gov.service.payments.commons.utils.healthchecks.DatabaseHealthCheck;
import uk.gov.service.payments.commons.utils.metrics.DatabaseMetricsService;
import uk.gov.service.payments.logging.GovUkPayDropwizardRequestJsonLogLayoutFactory;
import uk.gov.service.payments.logging.LoggingFilter;
import uk.gov.service.payments.logging.LogstashConsoleAppenderFactory;

import java.util.concurrent.TimeUnit;

import static java.util.EnumSet.of;
import static javax.servlet.DispatcherType.REQUEST;

public class PaymentsApp extends Application<PaymentsConfig> {

    private static final Logger logger = LoggerFactory.getLogger(PaymentsApp.class);
    
    public static void main(String[] args) throws Exception {
        new PaymentsApp().run(args);
    }
    private static final int METRICS_COLLECTION_PERIOD_SECONDS = 30;

    private final HibernateBundle<PaymentsConfig> hibernate = new HibernateBundle<>(
            PaymentEntity.class,
            TokenEntity.class
    ) {
        @Override
        public DataSourceFactory getDataSourceFactory(PaymentsConfig configuration) {
            return configuration.getDataSourceFactory();
        }
    };
    
    @Override
    public void run(PaymentsConfig configuration, Environment environment) {
        final Injector injector = Guice.createInjector(new PaymentsModule(configuration, environment, hibernate));

        environment.servlets().addFilter("LoggingFilter", new LoggingFilter())
                .addMappingForUrlPatterns(of(REQUEST), true, "/v1/*");

        environment.healthChecks().register("ping", new Ping());
        environment.healthChecks().register("database", new DatabaseHealthCheck(configuration.getDataSourceFactory()));
        environment.jersey().register(injector.getInstance(HealthCheckResource.class));

        initialiseMetrics(configuration, environment);
    }

    @Override
    public void initialize(Bootstrap<PaymentsConfig> bootstrap){
        bootstrap.setConfigurationSourceProvider(
                new SubstitutingSourceProvider(bootstrap.getConfigurationSourceProvider(),
                        new EnvironmentVariableSubstitutor(false))
        );

        bootstrap.addBundle(new MigrationsBundle<>() {
            @Override
            public DataSourceFactory getDataSourceFactory(PaymentsConfig configuration) {
                return configuration.getDataSourceFactory();
            }
        });

        bootstrap.addBundle(hibernate);
        bootstrap.getObjectMapper().getSubtypeResolver().registerSubtypes(LogstashConsoleAppenderFactory.class);
        bootstrap.getObjectMapper().getSubtypeResolver().registerSubtypes(GovUkPayDropwizardRequestJsonLogLayoutFactory.class);
    }

    private void initialiseMetrics(PaymentsConfig configuration, Environment environment) {
        DatabaseMetricsService metricsService = new DatabaseMetricsService(configuration.getDataSourceFactory(), environment.metrics(), "payments");

        environment
                .lifecycle()
                .scheduledExecutorService("metricscollector")
                .threads(1)
                .build()
                .scheduleAtFixedRate(metricsService::updateMetricData, 0, METRICS_COLLECTION_PERIOD_SECONDS / 2, TimeUnit.SECONDS);

        CollectorRegistry collectorRegistry = CollectorRegistry.defaultRegistry;
        collectorRegistry.register(new DropwizardExports(environment.metrics()));
        environment.admin().addServlet("prometheusMetrics", new MetricsServlet(collectorRegistry)).addMapping("/metrics");
    }
}
