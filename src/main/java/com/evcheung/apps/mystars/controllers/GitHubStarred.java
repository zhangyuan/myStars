package com.evcheung.apps.mystars.controllers;

//@JsonIgnoreProperties(ignoreUnknown = true)
public class GitHubStarred {
    private String name;
    private String description;
    private String url;

    public GitHubOwner getOwner() {
        return owner;
    }

    public void setOwner(GitHubOwner owner) {
        this.owner = owner;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private GitHubOwner owner;
}
