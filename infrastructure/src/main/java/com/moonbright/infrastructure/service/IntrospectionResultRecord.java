package com.moonbright.infrastructure.service;

public record IntrospectionResultRecord(Boolean active,String sub) {
    public String subject() {
        return sub();
    }
}
