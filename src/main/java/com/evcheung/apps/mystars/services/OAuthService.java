package com.evcheung.apps.mystars.services;

import com.github.scribejava.apis.GitHubApi;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.oauth.OAuth20Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponents;

import java.io.IOException;

@Component
public class OAuthService {
    @Autowired
    AppConfig config;

    public UriComponents getCallback() {
        return ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/auth/github/callback")
                .build();
    }

    public String authorizeUrl(String callback, String state){
        OAuth20Service service = getServiceBuilder()
                .callback(callback)
                .state(state)

                .build(GitHubApi.instance());

        return service.getAuthorizationUrl();
    }

    private ServiceBuilder getServiceBuilder() {
        return new ServiceBuilder()
                .apiKey(config.getClientId())
                .apiSecret(config.getClientSecret());
    }

    public String authorize(String code, String callback) throws IOException {
        OAuth20Service service = getServiceBuilder().callback(callback).build(GitHubApi.instance());
        OAuth2AccessToken accessToken = service.getAccessToken(code);
        return accessToken.getAccessToken();
    }
}
