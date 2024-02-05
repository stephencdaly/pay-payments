package uk.gov.pay.payments.tokens.resource;

import io.dropwizard.hibernate.UnitOfWork;
import io.swagger.v3.oas.annotations.tags.Tag;
import uk.gov.pay.payments.tokens.TokenService;

import javax.inject.Inject;
import javax.ws.rs.*;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/v1/token")
@Consumes(APPLICATION_JSON)
@Produces(APPLICATION_JSON)
@Tag(name = "Tokens")
public class TokenResource {
    private final TokenService tokenService;

    @Inject
    public TokenResource(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @UnitOfWork
    @GET
    @Path("/{token}")
    public TokenResponse getToken(@PathParam("token") String token) {
        return tokenService.getPaymentForToken(token);
    }

    @UnitOfWork
    @POST
    @Path("/{token}/used")
    public void markTokenUsed(@PathParam("token") String token) {
        tokenService.markAsUsed(token);
    }
}
