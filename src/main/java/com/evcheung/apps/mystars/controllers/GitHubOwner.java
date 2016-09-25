package com.evcheung.apps.mystars.controllers;

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
