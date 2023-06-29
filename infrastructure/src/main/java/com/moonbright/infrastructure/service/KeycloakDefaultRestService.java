package com.moonbright.infrastructure.service;

import com.moonbright.infrastructure.configs.ConfigStore;
import com.moonbright.infrastructure.service.serviceRequest.AppAccessTokenResponse;
import com.moonbright.infrastructure.util.JsonUtility;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.HttpHeaders;
import lombok.NoArgsConstructor;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.keycloak.common.VerificationException;
import org.keycloak.representations.idm.UserRepresentation;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
@NoArgsConstructor
public class KeycloakDefaultRestService implements KeycloakRestService {

    private  KeycloakDeploymentService keycloakDeploymentService;
    private  ConfigStore configStore;
    private  ApplicationAccessTokenHolder applicationAccessTokenHolder;
    private JsonUtility jsonUtility;


    @Inject
    public KeycloakDefaultRestService(KeycloakDeploymentService keycloakDeploymentService, ConfigStore configStore, ApplicationAccessTokenHolder applicationAccessTokenHolder, JsonUtility jsonUtility) {
        this.keycloakDeploymentService = keycloakDeploymentService;
        this.configStore = configStore;
        this.applicationAccessTokenHolder = applicationAccessTokenHolder;
        this.jsonUtility = jsonUtility;
    }


    public List<UserRepresentation> getRealmUserList(String name,String lastName) {
        List<UserRepresentation> userRepresentationList  = null;
       try{
           var userListResponse =
                   this.keycloakDeploymentService.getHttpClient().execute(createRealmUserListRequest(name,lastName));
           userRepresentationList =
                   jsonUtility.getJsonb().fromJson(userListResponse.getEntity().getContent(),new ArrayList<UserRepresentation>(){}.getClass().getGenericSuperclass());
       } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return userRepresentationList;
    }


    public String getAccessToken(){
        AppAccessTokenResponse accessTokenResponse = null;
        try {
          var tokenResponse = this.keycloakDeploymentService.getHttpClient().execute(createTokenRequest());
         accessTokenResponse =  jsonUtility.getJsonb().fromJson(tokenResponse.getEntity().getContent(), AppAccessTokenResponse.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return accessTokenResponse.access_token();
    }

    private HttpUriRequest createTokenRequest() throws UnsupportedEncodingException {
        String user = this.configStore.getProperty(ConfigStore.PropertyConstants.REALM_ADMIN.getKey());
        String pass = this.configStore.getProperty(ConfigStore.PropertyConstants.REALM_ADMIN_PASS.getKey());
        String clientId = this.configStore.getProperty(ConfigStore.PropertyConstants.CLIENT_ADMIN_ID.getKey());
        String realm = "master";
        String host = keycloakDeploymentService.getKeycloakDeployment().getAuthServerBaseUrl();
        String tokenEndpoint = MessageFormat.format(this.configStore.getProperty(ConfigStore.PropertyConstants.TOKEN_API.getKey()),realm);
        var request = new HttpPost(host.concat(tokenEndpoint));
        List<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("grant_type","password"));
        nameValuePairs.add(new BasicNameValuePair("password",pass));
        nameValuePairs.add(new BasicNameValuePair("username",user));
        nameValuePairs.add(new BasicNameValuePair("client_id",clientId));
        request.setEntity(new UrlEncodedFormEntity(nameValuePairs));
        return request;
    }

    private HttpUriRequest createRealmUserListRequest(String name,String lastName) throws URISyntaxException, VerificationException {
            String realm = keycloakDeploymentService.getKeycloakDeployment().getRealm();
            String token = applicationAccessTokenHolder.getAccessTokenString();
            String userQueryEndpoint = this.configStore.getProperty("user-query-endpoint");
            String endpoint = MessageFormat.format(userQueryEndpoint,realm);
            String host = keycloakDeploymentService.getKeycloakDeployment().getAuthServerBaseUrl();
            URIBuilder uriBuilder = new URIBuilder(host.concat(endpoint))
                    .addParameter("q","userType:professional");
            uriBuilder = name != null && !name.isEmpty() ? uriBuilder.addParameter("firstName",name) : uriBuilder;
            uriBuilder = lastName != null && !lastName.isEmpty() ? uriBuilder.addParameter("lastName",lastName) : uriBuilder;
            URI uri = uriBuilder.build();
            HttpUriRequest request = new HttpGet(uri);
            request.addHeader(HttpHeaders.AUTHORIZATION,"Bearer "+token);
            return request;

    }



}
