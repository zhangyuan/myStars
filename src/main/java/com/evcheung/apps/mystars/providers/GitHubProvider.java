package com.evcheung.apps.mystars.providers;

import org.springframework.stereotype.Component;

@Component
public class GitHubProvider {
    public GitHubProvider() {
        apiPrefix = "https://api.github.com";
    }

    public String getApiPrefix() {
        return apiPrefix;
    }

    public void setApiPrefix(String apiPrefix) {
        this.apiPrefix = apiPrefix;
    }

    public String starsUrl(String username){
        return String.join("/", apiPrefix, "user", username, "starred");
    }

    private String apiPrefix;
}
