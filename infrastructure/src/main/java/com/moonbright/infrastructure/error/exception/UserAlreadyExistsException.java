package com.moonbright.infrastructure.error.exception;

import jakarta.ws.rs.core.Response;

public class UserAlreadyExistsException extends AppBaseException{

    public UserAlreadyExistsException(Object entity){
        super(Response.status(500).entity(entity).build());
    }
}
