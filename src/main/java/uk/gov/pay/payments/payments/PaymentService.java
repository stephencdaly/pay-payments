package uk.gov.pay.payments.payments;

import uk.gov.pay.payments.app.LinksConfig;
import uk.gov.pay.payments.app.PaymentsConfig;
import uk.gov.pay.payments.payments.dao.PaymentDao;
import uk.gov.pay.payments.payments.dao.PaymentEntity;
import uk.gov.pay.payments.payments.resource.CreatePaymentRequest;
import uk.gov.pay.payments.payments.resource.PaymentResponse;
import uk.gov.pay.payments.tokens.dao.TokenDao;
import uk.gov.pay.payments.tokens.dao.TokenEntity;
import uk.gov.pay.payments.util.IdGenerator;

import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;
import java.time.InstantSource;

public class PaymentService {
    private final PaymentDao paymentDao;
    private final TokenDao tokenDao;
    private final InstantSource instantSource;
    private final IdGenerator idGenerator;
    private final LinksConfig linksConfig;

    @Inject
    public PaymentService(PaymentDao paymentDao,
                          TokenDao tokenDao,
                          InstantSource instantSource,
                          IdGenerator idGenerator,
                          PaymentsConfig config
    ) {
        this.paymentDao = paymentDao;
        this.tokenDao = tokenDao;
        this.instantSource = instantSource;
        this.idGenerator = idGenerator;
        this.linksConfig = config.getLinks();
    }
    
    public PaymentResponse createPayment( long gatewayAccountId, CreatePaymentRequest createPaymentRequest) {
        var paymentEntity = PaymentEntity.from(gatewayAccountId, createPaymentRequest, idGenerator.newExternalId(), instantSource.instant());
        paymentDao.create(paymentEntity);
        var tokenEntity = TokenEntity.generateNewTokenFor(paymentEntity);
        tokenDao.create(tokenEntity);
        return PaymentResponse.withNextUrl(paymentEntity, tokenEntity, linksConfig);
    }
    
    public PaymentResponse getPaymentByExternalIdAndGatewayAccountId(String paymentExternalId, long gatewayAccountId) {
        var paymentEntity = paymentDao.findByExternalIdAndGatewayAccountId(paymentExternalId, gatewayAccountId)
                .orElseThrow(() -> new WebApplicationException("Could not find payment", 404));
        
        return PaymentResponse.withoutNextUrl(paymentEntity);
    }
    
    public PaymentResponse getPaymentByExternalId(String paymentExternalId) {
        var paymentEntity = paymentDao.findByExternalId(paymentExternalId)
                .orElseThrow(() -> new WebApplicationException("Could not find payment", 404));
        
        return PaymentResponse.withoutNextUrl(paymentEntity);
    }
}
