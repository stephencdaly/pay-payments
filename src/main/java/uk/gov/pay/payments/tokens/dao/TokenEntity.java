package uk.gov.pay.payments.tokens.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;
import uk.gov.pay.payments.payments.dao.PaymentEntity;

import javax.persistence.*;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

@NamedQuery(
        name = TokenEntity.GET_BY_TOKEN,
        query = "select t from TokenEntity t where token = :token"
)

@Entity
@SequenceGenerator(name = "tokens_id_seq", sequenceName = "tokens_id_seq", allocationSize = 1)
@Table(name = "tokens")
public class TokenEntity {
    public static final String GET_BY_TOKEN = "Token.get_by_secure_redirect_token";
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tokens_id_seq")
    @JsonIgnore
    private Long id;

    @Column(name = "secure_redirect_token")
    private String token;

    @Column(name = "created_date")
    private OffsetDateTime createdDate;

    @ManyToOne
    @JoinColumn(name = "payment_id", nullable = false)
    private PaymentEntity paymentEntity;

    @Column(name = "used")
    private boolean used;
    
    public static TokenEntity generateNewTokenFor(PaymentEntity payment) {
        TokenEntity tokenEntity = new TokenEntity();
        tokenEntity.setPaymentEntity(payment);
        tokenEntity.setCreatedDate(payment.getCreatedDate());
        tokenEntity.setToken(UUID.randomUUID().toString());
        tokenEntity.setUsed(false);
        return tokenEntity;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public OffsetDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = OffsetDateTime.ofInstant(createdDate, ZoneOffset.UTC);
    }

    public PaymentEntity getPaymentEntity() {
        return paymentEntity;
    }

    public void setPaymentEntity(PaymentEntity paymentEntity) {
        this.paymentEntity = paymentEntity;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }
}


