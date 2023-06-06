package com.moonbright.infrastructure.error.exception;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

public class AppBaseException extends WebApplicationException {
    public AppBaseException(Response response){
        super(response);
    }
}
