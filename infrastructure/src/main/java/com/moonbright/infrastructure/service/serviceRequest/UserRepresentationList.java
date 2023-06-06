package com.moonbright.infrastructure.service.serviceRequest;

import org.keycloak.representations.idm.UserRepresentation;

import java.util.List;

public record UserRepresentationList(List<UserRepresentation> userRepresentationList) {
}
