package com.evcheung.apps.mystars.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="stars")
public class Star {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String repositoryName;

    @OneToMany(cascade= CascadeType.ALL, fetch= FetchType.LAZY)
    List<Tag> tags;

    private String owner;

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getOwner() {
        return owner;
    }

    public void setRepositoryName(String repositoryName) {
        this.repositoryName = repositoryName;
    }

    public String getRepositoryName() {
        return repositoryName;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public List<Tag> getTags() {
        return tags;
    }
}
