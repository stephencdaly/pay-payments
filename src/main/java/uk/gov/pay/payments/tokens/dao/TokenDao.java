package uk.gov.pay.payments.tokens.dao;

import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import javax.inject.Inject;
import java.util.Optional;

public class TokenDao extends AbstractDAO<TokenEntity> {
    
    @Inject
    public TokenDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
    
    public TokenEntity create(TokenEntity token) {
        persist(token);
        return token;
    }
    
    public Optional<TokenEntity> findByToken(String token) {
        return namedTypedQuery(TokenEntity.GET_BY_TOKEN)
                .setParameter("token", token)
                .getResultList()
                .stream()
                .findFirst();
    }
}
