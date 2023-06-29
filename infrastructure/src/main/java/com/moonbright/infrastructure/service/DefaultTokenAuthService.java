package com.moonbright.infrastructure.service;

import com.moonbright.infrastructure.configs.ConfigStore;
import com.moonbright.infrastructure.error.ErrorCodeAndDescription;
import com.moonbright.infrastructure.error.exception.BaseErrorResponse;
import com.moonbright.infrastructure.util.JsonUtility;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.message.BasicNameValuePair;

import java.nio.charset.Charset;
import java.text.MessageFormat;
import java.util.Base64;
import java.util.List;
import java.util.Objects;

@ApplicationScoped
public class DefaultTokenAuthService implements TokenAuthService{

    private static final Log log = LogFactory.getLog(DefaultTokenAuthService.class);
    private final KeycloakDeploymentService keycloakDeploymentService;
    private final ConfigStore configStore;
    private final JsonUtility jsonUtility;

    @Inject
    public DefaultTokenAuthService(KeycloakDeploymentService keycloakDeploymentService, ConfigStore configStore, JsonUtility jsonUtility){
      this.keycloakDeploymentService = keycloakDeploymentService;
      this.configStore = configStore;
        this.jsonUtility = jsonUtility;
    }
    @Override
    public IntrospectionResultRecord authenticate(ContainerRequestContext requestContext) {
        IntrospectionResultRecord accessTokenResult = null;
         String clientId = this.configStore.getProperty(ConfigStore.PropertyConstants.CLIENT_ID.getKey());
         String clientSecret = this.configStore.getProperty(ConfigStore.PropertyConstants.CLIENT_SECRET.getKey());
         String introspectEndpoint = this.configStore.getProperty(ConfigStore.PropertyConstants.INTROSPECT_API.getKey());
         String token = initialVerification(requestContext);
        try{
            var introspectionResponse = this.keycloakDeploymentService.getHttpClient().execute(createRequest(
                            clientId,
                            clientSecret,
                            introspectEndpoint,
                            token,
                            keycloakDeploymentService.getKeycloakDeployment().getAuthServerBaseUrl(),
                            keycloakDeploymentService.getKeycloakDeployment().getRealm()));
            accessTokenResult = jsonUtility.getJsonb().fromJson(introspectionResponse.getEntity().getContent(),IntrospectionResultRecord.class);
            if (!accessTokenResult.active())
                throwVerificationException(ErrorCodeAndDescription.CLIENT_TOKEN_VERIFICATION_FAILED_ERROR_CODE);
        }
        catch (Exception e){
            log.error(e);
            throwVerificationException(ErrorCodeAndDescription.CLIENT_TOKEN_VERIFICATION_FAILED_ERROR_CODE);
        }
        return accessTokenResult;
    }



    private HttpUriRequest createRequest(String clientID,String clientSecret,String introspectEndpoint, String token, String host,String realm) {
        introspectEndpoint = MessageFormat.format(introspectEndpoint,realm);
        var request =  new HttpPost(host.concat(introspectEndpoint));
        request.addHeader(HttpHeaders.CONTENT_TYPE,MediaType.APPLICATION_FORM_URLENCODED);
        var valueNamePairs =  List.of(new BasicNameValuePair("token",token));
        request.setEntity(new UrlEncodedFormEntity(valueNamePairs, Charset.defaultCharset()));
        request.addHeader(HttpHeaders.AUTHORIZATION, "Basic " + Base64.getEncoder().encodeToString(clientID.concat(":").concat(clientSecret).getBytes()));
        return  request;
    }


    private String initialVerification(ContainerRequestContext requestContext){
        String  token =
                requestContext.getHeaders().get(HttpHeaders.AUTHORIZATION) != null ?

                        requestContext.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0).split("Bearer ")[1]
                        :
                        null;
        if (Objects.isNull(token)){
            throwVerificationException(ErrorCodeAndDescription.CLIENT_TOKEN_STRUCTURE_IS_NOT_VALID);
        }
        return token;
    }

    private static void throwVerificationException(ErrorCodeAndDescription e) {
        throw new WebApplicationException(
                Response
                        .status(401)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                        .entity(BaseErrorResponse.fromErrorCode(e)).build());
    }
}
