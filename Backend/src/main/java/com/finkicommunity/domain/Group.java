package com.finkicommunity.domain;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.LinkedHashSet;
import java.util.Set;

@NodeEntity
public class Group {
    @Id @GeneratedValue
    private Long id;
    private String code;
    private String name;
    private String description;

    private ImageModel groupPicture;

    @Relationship(type = "MODERATOR", direction = Relationship.UNDIRECTED)
    private Set<User> moderators;


    public Group() {
        moderators = new LinkedHashSet<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ImageModel getGroupPicture() {
        return groupPicture;
    }

    public void setGroupPicture(ImageModel groupPicture) {
        this.groupPicture = groupPicture;
    }

    public Set<User> getModerators() {
        return moderators;
    }

    public void addModerator(User moderator) {
        this.moderators.add(moderator);
    }

    @Override
    public String toString() {
        return id + " " + code + " " + name + " " + description;
    }
}
