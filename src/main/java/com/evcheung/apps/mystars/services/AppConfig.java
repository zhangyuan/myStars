package com.evcheung.apps.mystars.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AppConfig {
    public String getClientId() {
        return clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    @Value("${client-id}")
    private String clientId;
    @Value("${client-secret}")
    private String clientSecret;
}
