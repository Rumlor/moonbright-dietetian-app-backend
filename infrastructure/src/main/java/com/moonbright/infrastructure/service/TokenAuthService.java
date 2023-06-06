package com.moonbright.infrastructure.service;

import jakarta.ws.rs.container.ContainerRequestContext;
import org.keycloak.common.VerificationException;
import org.keycloak.representations.AccessToken;

public interface TokenAuthService {
    IntrospectionResultRecord authenticate(ContainerRequestContext requestContext) throws VerificationException;

}
