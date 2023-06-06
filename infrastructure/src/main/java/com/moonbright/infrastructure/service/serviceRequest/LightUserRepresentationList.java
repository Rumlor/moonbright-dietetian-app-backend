package com.moonbright.infrastructure.service.serviceRequest;

public record LightUserRepresentationList(String firstName,
                                          String lastName,
                                          String email,
                                          String location,
                                          String district,
                                          String id,
                                          Long createdTimestamp) {
}
