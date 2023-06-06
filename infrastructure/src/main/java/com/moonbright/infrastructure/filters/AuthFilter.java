package com.moonbright.infrastructure.filters;

import com.moonbright.infrastructure.error.ErrorCodeAndDescription;
import com.moonbright.infrastructure.error.exception.BaseErrorResponse;
import com.moonbright.infrastructure.service.IntrospectionResultRecord;
import com.moonbright.infrastructure.service.TokenAuthService;
import jakarta.inject.Inject;
import jakarta.ws.rs.HttpMethod;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.PreMatching;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.keycloak.common.VerificationException;

@Provider
@PreMatching
public class AuthFilter implements ContainerRequestFilter {

    private static final Log log = LogFactory.getLog(AuthFilter.class);
    private final TokenAuthService tokenAuthService;

    @Inject
    public AuthFilter(TokenAuthService tokenAuthService){
        this.tokenAuthService = tokenAuthService;
    }

    @Override
    public void filter(ContainerRequestContext requestContext)  {
        if(!requestContext.getRequest().getMethod().equals(HttpMethod.OPTIONS)) {
            try {
                IntrospectionResultRecord accessToken = tokenAuthService.authenticate(requestContext);

                refactorRequestHeaderForUID(requestContext, accessToken);
            }
            catch (VerificationException e) {
                log.info(e.getMessage());
                throwVerificationException(ErrorCodeAndDescription.CLIENT_TOKEN_VERIFICATION_FAILED_ERROR_CODE);
            }
        }
    }

    private void refactorRequestHeaderForUID(ContainerRequestContext requestContext, IntrospectionResultRecord accessToken) {
        requestContext.getHeaders().add("uid", accessToken.subject());
    }




    private static void throwVerificationException(ErrorCodeAndDescription e) {
        throw new WebApplicationException(
                Response
                        .status(401)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                        .entity(BaseErrorResponse.fromErrorCode(e)).build());
    }
}
