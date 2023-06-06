package com.moonbright.infrastructure.error.exception;

import jakarta.ws.rs.core.Response;

public class UserIdNotFoundException extends AppBaseException{

    public UserIdNotFoundException(Object entity){
        super(Response.status(404).entity(entity).build());
    }
}
