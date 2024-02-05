package uk.gov.pay.payments.payments.resource;

import io.dropwizard.hibernate.UnitOfWork;
import io.swagger.v3.oas.annotations.tags.Tag;
import uk.gov.pay.payments.payments.PaymentService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/v1")
@Consumes(APPLICATION_JSON)
@Produces(APPLICATION_JSON)
@Tag(name = "Payments")
public class PaymentResource {

    private final PaymentService paymentService;

    @Inject
    public PaymentResource(PaymentService paymentService) {
        this.paymentService = paymentService;
    }
    
    @UnitOfWork
    @POST
    @Path("/payment")
    public PaymentResponse createPayment(@NotNull @Valid CreatePaymentRequest request) {
        return paymentService.createPayment(request);
    }
    
    @UnitOfWork
    @GET
    @Path("/account/{accountId}/payment/{paymentExternalId}")
    public PaymentResponse getPayment(@PathParam("accountId") long accountId,
                                      @PathParam("paymentExternalId") String paymentExternalId) {
        return paymentService.getPaymentByExternalIdAndGatewayAccountId(paymentExternalId, accountId);
    }
}
