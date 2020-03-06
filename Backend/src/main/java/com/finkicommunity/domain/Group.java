package com.finkicommunity.domain;

import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.*;

import java.util.Set;

@NodeEntity
@NoArgsConstructor
public class Group {
    @Id @GeneratedValue
    private Long id;
    private String code;
    private String name;
    private String description;
    private String GroupPictureUrl;

//    private Set<User> moderators;

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

    @Override
    public String toString() {
        return id + " " + code + " " + name + " " + description;
    }
}
