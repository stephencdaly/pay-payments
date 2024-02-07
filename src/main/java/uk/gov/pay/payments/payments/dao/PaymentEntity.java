package uk.gov.pay.payments.payments.dao;

import uk.gov.pay.payments.payments.resource.CreatePaymentRequest;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@NamedQuery(
        name = PaymentEntity.GET_BY_EXTERNAL_ID_AND_GATEWAY_ACCOUNT_ID,
        query = "select p from PaymentEntity p where externalId = :externalId and gatewayAccountId = :gatewayAccountId"
)

@NamedQuery(
        name = PaymentEntity.GET_BY_EXTERNAL_ID,
        query = "select p from PaymentEntity  p where externalId = :externalId"
)

@Entity
@SequenceGenerator(name="payments_id_seq", sequenceName = "payments_id_seq", allocationSize = 1)
@Table(name = "payments")
public class PaymentEntity {
    public static final String GET_BY_EXTERNAL_ID_AND_GATEWAY_ACCOUNT_ID = "Payment.get_by_external_id_and_gateway_account_id";
    public static final String GET_BY_EXTERNAL_ID = "Payment.get_by_external_id";
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "payments_id_seq")
    private long id;

    @Column(name = "created_date")
    private OffsetDateTime createdDate;
    
    @Column(name = "external_id")
    private String externalId;

    // TODO: we want this to be the service_id instead, but need to make changes to publicauth to store service_id and live/test against an API key first
    @Column(name = "gateway_account_id")
    private long gatewayAccountId;


    @Column(name = "amount")
    private long amount;

    @Column(name = "reference")
    private String reference;

    @Column(name = "description")
    private String description;
    
    @Column(name = "return_url")
    private String returnUrl;

    @Column(name = "delayed_capture")
    private boolean delayedCapture;
    
    @Column(name = "moto")
    private boolean moto;
    

    public static PaymentEntity from(long gatewayAccountId, CreatePaymentRequest createPaymentRequest, String externalId, Instant createdDate) {
        var entity = new PaymentEntity();
        entity.setGatewayAccountId(gatewayAccountId);
        entity.setExternalId(externalId);
        entity.setCreatedDate(createdDate);
        entity.setAmount(createPaymentRequest.amount());
        entity.setReference(createPaymentRequest.reference());
        entity.setDescription(createPaymentRequest.description());
        entity.setReturnUrl(createPaymentRequest.returnUrl());
        entity.setDelayedCapture(createPaymentRequest.delayedCapture());
        return entity;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getGatewayAccountId() {
        return gatewayAccountId;
    }

    public void setGatewayAccountId(long gatewayAccountId) {
        this.gatewayAccountId = gatewayAccountId;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    public Instant getCreatedDate() {
        return createdDate.toInstant();
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = OffsetDateTime.ofInstant(createdDate, ZoneOffset.UTC);
    }

    public boolean isDelayedCapture() {
        return delayedCapture;
    }

    public void setDelayedCapture(boolean delayedCapture) {
        this.delayedCapture = delayedCapture;
    }

    public boolean isMoto() {
        return moto;
    }

    public void setMoto(boolean moto) {
        this.moto = moto;
    }
}
