package com.moonbright.infrastructure.service;

import org.keycloak.representations.idm.UserRepresentation;

import java.util.List;

public interface KeycloakRestService {
    List<UserRepresentation> getRealmUserList(String name, String lastName);
    String getAccessToken();
}
