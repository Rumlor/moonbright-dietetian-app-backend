package com.moonbright.infrastructure.service;

import jakarta.enterprise.context.ApplicationScoped;
import org.apache.http.client.HttpClient;
import org.keycloak.adapters.KeycloakDeployment;
import org.keycloak.adapters.KeycloakDeploymentBuilder;
import org.keycloak.representations.adapters.config.AdapterConfig;

@ApplicationScoped
public class KeycloakDeploymentService {
    private KeycloakDeployment keycloakDeployment;
    private AdapterConfig adapterConfig;

    public KeycloakDeploymentService(){
        deploy();
    }

    public KeycloakDeployment getKeycloakDeployment() {
        return keycloakDeployment;
    }

    public HttpClient getHttpClient(){
        return this.keycloakDeployment.getClient();
    }

    private void deploy(){
        adapterConfig = new AdapterConfig();
        adapterConfig.setAuthServerUrl("http://localhost:8080/");
        adapterConfig.setRealm("moonbright");
        adapterConfig.setResource("moonbright-demo-client");
        adapterConfig.setPublicClient(true);
        keycloakDeployment = KeycloakDeploymentBuilder.build(adapterConfig);
    }
}
