package com.moonbright.infrastructure.filters;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.container.PreMatching;
import jakarta.ws.rs.ext.Provider;


@Provider
@PreMatching
public class CorsFilter implements ContainerResponseFilter {

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) {
        responseContext.getHeaders().add("Access-Control-Allow-Origin", "*");
        responseContext.getHeaders().add("Access-Control-Allow-Methods","*");
        responseContext.getHeaders().add("Access-Control-Request-Headers","uid");
        responseContext.getHeaders().add("Access-Control-Allow-Headers","uid");
        responseContext.getHeaders().add("Access-Control-Allow-Headers","Authorization");
        responseContext.getHeaders().add("Access-Control-Allow-Headers","Content-Type");
        responseContext.getHeaders().add("Access-Control-Request-Method","*");
        responseContext.getHeaders().add("Access-Control-Expose-Headers","*");
    }
}
