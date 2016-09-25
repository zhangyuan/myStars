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

    @Value("${client-id:client_id_place_holder}")
    private String clientId;
    @Value("${client-secret:client_secret_place_holder}")
    private String clientSecret;
}
