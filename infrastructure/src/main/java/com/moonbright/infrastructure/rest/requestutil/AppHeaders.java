package com.moonbright.infrastructure.rest.requestutil;

import lombok.Getter;

@Getter
public enum AppHeaders {
    UID("uid");

    private final String headerValue;
    AppHeaders(String headerValue) {
        this.headerValue = headerValue;
    }
}
