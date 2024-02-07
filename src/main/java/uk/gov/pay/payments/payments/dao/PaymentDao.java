package uk.gov.pay.payments.payments.dao;

import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import javax.inject.Inject;
import java.util.Optional;

public class PaymentDao extends AbstractDAO<PaymentEntity> {
    
    @Inject
    public PaymentDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
    
    public PaymentEntity create(PaymentEntity payment) {
        persist(payment);
        return payment;
    }
    
    public Optional<PaymentEntity> findByExternalIdAndGatewayAccountId(String paymentExternalId, long gatewayAccountId) {
        return namedTypedQuery(PaymentEntity.GET_BY_EXTERNAL_ID_AND_GATEWAY_ACCOUNT_ID)
                .setParameter("externalId", paymentExternalId)
                .setParameter("gatewayAccountId", gatewayAccountId)
                .getResultList()
                .stream()
                .findFirst();
    }

    public Optional<PaymentEntity> findByExternalId(String paymentExternalId) {
        return namedTypedQuery(PaymentEntity.GET_BY_EXTERNAL_ID)
                .setParameter("externalId", paymentExternalId)
                .getResultList()
                .stream()
                .findFirst();
    }
}
