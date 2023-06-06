package com.moonbright.infrastructure.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.keycloak.TokenVerifier;
import org.keycloak.common.VerificationException;
import org.keycloak.representations.AccessToken;

@ApplicationScoped
public class ApplicationAccessTokenHolder {
    private  AccessToken accessToken;
    private  String accessTokenString;
    private KeycloakRestService keycloakDefaultRestService;

    @Inject
    public ApplicationAccessTokenHolder(KeycloakRestService keycloakDefaultRestService) {
        this.keycloakDefaultRestService = keycloakDefaultRestService;
    }

    public ApplicationAccessTokenHolder(){}

    public synchronized AccessToken getAccessToken() throws VerificationException {
        if (this.accessToken == null || this.accessToken.isExpired()){
            refreshAccessToken();
        }
        return accessToken;
    }

    private synchronized void refreshAccessToken() throws VerificationException {
        String newToken = keycloakDefaultRestService.getAccessToken();
        this.accessToken = TokenVerifier.create(newToken,AccessToken.class).getToken();
        this.accessTokenString = newToken;
    }

    public  synchronized String getAccessTokenString() throws VerificationException {
        if (accessTokenString == null || accessTokenString.isEmpty() || this.accessToken.isExpired()){
            getAccessToken();
        }
        return accessTokenString;
    }

    public synchronized  void setAccessToken(AccessToken accessToken) {
        this.accessToken = accessToken;
    }

    public synchronized  void setAccessTokenString(String accessTokenString) {
        this.accessTokenString = accessTokenString;
    }
}
