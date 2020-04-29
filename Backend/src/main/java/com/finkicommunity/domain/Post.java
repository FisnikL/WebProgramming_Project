package com.finkicommunity.domain;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.HashSet;
import java.util.Set;

@NodeEntity
public class Post {

    @Id @GeneratedValue
    private Long id;
    private String title;
    private String content;
    private long created;

    @Relationship(type = "GROUP_POST", direction = Relationship.UNDIRECTED)
    private Group group;

    @Relationship(type = "USER_POST", direction = Relationship.UNDIRECTED)
    private User user;

    @Relationship(type = "LIKES", direction = Relationship.UNDIRECTED)
    private Set<User> likes;

    public Post() {
        created = System.currentTimeMillis();
        likes = new HashSet<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getCreated() {
        return created;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<User> getLikes() {
        return likes;
    }

    @Override
    public String toString() {
        return id + " " + title + " " + content + " " + group.getCode() + " " + user.getUsername() + " " + likes.size();
    }
}
