package com.moonbright.infrastructure.error.exception.mapper;

import com.moonbright.infrastructure.error.exception.BaseErrorResponse;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class GenericRuntimeExceptionMapper implements ExceptionMapper<RuntimeException> {

    @Override
    public Response toResponse(RuntimeException exception) {
        exception.printStackTrace();
        if (exception instanceof WebApplicationException webApplicationException){
            return webApplicationException.getResponse();
        }
        else
            return Response.status(500).entity(BaseErrorResponse.fromException(exception)).build();
    }
}
