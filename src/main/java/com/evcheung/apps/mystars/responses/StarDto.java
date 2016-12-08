package com.evcheung.apps.mystars.responses;

import com.evcheung.apps.mystars.entities.Star;

import java.util.List;
import java.util.stream.Collectors;

public class StarDto {
    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getRepositoryName() {
        return repositoryName;
    }

    public void setRepositoryName(String repositoryName) {
        this.repositoryName = repositoryName;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    private String owner = null;
    private String repositoryName = null;
    List<String> tags;

    public StarDto() {
    }

    public StarDto(Star star) {
        this.owner = star.getOwner();
        this.repositoryName = star.getRepositoryName();
        this.tags = star.getTags().stream().map(x -> x.getTitle()).collect(Collectors.toList());
    }
}
