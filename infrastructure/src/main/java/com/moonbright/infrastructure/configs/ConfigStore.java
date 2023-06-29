package com.moonbright.infrastructure.configs;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Singleton
@Startup
public class ConfigStore {
    private static final Log log = LogFactory.getLog(ConfigStore.class);
    private static Properties properties;
    public enum PropertyConstants{
        CLIENT_ID("client-id"),
        CLIENT_SECRET("client-secret"),
        CLIENT_ADMIN_ID("client-admin-id"),
        INTROSPECT_API("introspect-endpoint"),
        TOKEN_API("token-endpoint"),
        USER_API("user-query-endpoint"),
        REALM_ADMIN("realm-admin-user"),
        REALM_ADMIN_PASS("realm-admin-password");

        private String key;

        public String getKey(){
            return this.key;
        }

        PropertyConstants(String key) {
            this.key = key;
        }
    }

    @PostConstruct
    public void readProperties() {
        if (properties == null) {
                try (InputStream fileInputStream = this.getClass().getClassLoader().getResourceAsStream("configs/appConfigs.properties")) {
                    properties = new Properties();
                    properties.load(fileInputStream);
                } catch (IOException e) {
                    log.error("Error while opening config properties file.");
                }
        }
    }
    public String getProperty(String property){
        return properties.getProperty(property);
    }
}
