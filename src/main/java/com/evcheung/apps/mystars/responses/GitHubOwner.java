package com.evcheung.apps.mystars.responses;

//@JsonIgnoreProperties(ignoreUnknown = true)
public class GitHubOwner {
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    String login;
}
