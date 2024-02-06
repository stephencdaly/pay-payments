package uk.gov.pay.payments.payments.resource;

import io.dropwizard.hibernate.UnitOfWork;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
    @Path("/account/{accountId}/payment")
    @Operation(
            summary = "Create new payment",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK",
                            content = @Content(schema = @Schema(implementation = PaymentResponse.class))),
                    @ApiResponse(responseCode = "422", description = "Missing required parameters"),
                    @ApiResponse(responseCode = "400", description = "Invalid payload")
            }
    )
    public PaymentResponse createPayment(
            @PathParam("accountId") long accountId,
            @NotNull @Valid CreatePaymentRequest request) {
        return paymentService.createPayment(accountId, request);
    }
    
    @UnitOfWork
    @GET
    @Path("/account/{accountId}/payment/{paymentExternalId}")
    @Operation(
            summary = "Get payment by external ID and gateway account ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = PaymentResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Not found")
            }
    )
    public PaymentResponse getPayment(@PathParam("accountId") long accountId,
                                      @PathParam("paymentExternalId") String paymentExternalId) {
        return paymentService.getPaymentByExternalIdAndGatewayAccountId(paymentExternalId, accountId);
    }
}
