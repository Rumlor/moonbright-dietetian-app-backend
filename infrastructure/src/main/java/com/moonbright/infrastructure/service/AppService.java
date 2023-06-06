package com.moonbright.infrastructure.service;

import com.moonbright.infrastructure.rest.requestutil.AppHeaders;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;

import java.util.Objects;

public abstract class AppService {

    @Context
    private HttpHeaders httpHeaders;



    protected String getUserForRequest() {
        return Objects.requireNonNull(httpHeaders.getHeaderString(AppHeaders.UID.getHeaderValue()),"user id is null");
    }

}
