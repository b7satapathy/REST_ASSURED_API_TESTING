package com.spotify.oauth2.utils;

import java.util.Properties;

public class ConfigLoader {
    private final Properties properties;
    private static ConfigLoader configLoader;

    private ConfigLoader() {
        properties = PropertyUtils.propertyLoader("src/test/resources/config.properties");
    }

    public static ConfigLoader getInstance() {
        if (configLoader == null) {
            configLoader = new ConfigLoader();
        }
        return configLoader;
    }

    public String getClientId() {
        String prop = properties.getProperty("client_id");
        if(prop != null) return prop;
        else throw new RuntimeException("client_id not found in config.properties");
    }

    public String getClientSecret() {
        String prop = properties.getProperty("client_secret");
        if(prop != null) return prop;
        else throw new RuntimeException("client_secret not found in config.properties");
    }

    public String getGrantType() {
        String prop = properties.getProperty("grant_type");
        if(prop != null) return prop;
        else throw new RuntimeException("grant_type not found in config.properties");
    }

    public String getRefreshToken() {
        String prop = properties.getProperty("refresh_token");
        if(prop != null) return prop;
        else throw new RuntimeException("refresh_token not found in config.properties");
    }

    public String getUserId() {
        String prop = properties.getProperty("user_id");
        if(prop != null) return prop;
        else throw new RuntimeException("user_id not found in config.properties");
    }



}
