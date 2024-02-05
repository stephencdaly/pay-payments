package uk.gov.pay.payments.tokens;

import uk.gov.pay.payments.app.LinksConfig;
import uk.gov.pay.payments.app.PaymentsConfig;
import uk.gov.pay.payments.payments.resource.PaymentResponse;
import uk.gov.pay.payments.tokens.dao.TokenDao;
import uk.gov.pay.payments.tokens.dao.TokenEntity;
import uk.gov.pay.payments.tokens.resource.TokenResponse;

import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;

public class TokenService {
    private final TokenDao tokenDao;
    private final LinksConfig linksConfig;

    @Inject
    public TokenService(TokenDao tokenDao,
                        PaymentsConfig config) {
        this.tokenDao = tokenDao;
        this.linksConfig = config.getLinks();
    }
    
    public TokenResponse getPaymentForToken(String token) {
        TokenEntity tokenEntity = tokenDao.findByToken(token).orElseThrow(() -> new WebApplicationException("Token not found", 404));
        var paymentResponse = PaymentResponse.withoutNextUrl(tokenEntity.getPaymentEntity());
        return new TokenResponse(paymentResponse, tokenEntity.isUsed());
    }
    
    public void markAsUsed(String token) {
        TokenEntity tokenEntity = tokenDao.findByToken(token).orElseThrow(() -> new WebApplicationException("Token not found", 404));
        tokenEntity.setUsed(true);
    }
}
