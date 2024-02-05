package uk.gov.pay.payments.payments.resource;

import io.dropwizard.hibernate.UnitOfWork;
import io.swagger.v3.oas.annotations.tags.Tag;
import uk.gov.pay.payments.payments.PaymentService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/v1/payment")
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
    public PaymentResponse createPayment(@NotNull @Valid CreatePaymentRequest request) {
        return paymentService.createPayment(request);
    }
}
