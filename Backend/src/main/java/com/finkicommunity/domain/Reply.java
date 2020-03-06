package com.finkicommunity.domain;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.HashSet;
import java.util.Set;

@NodeEntity
public class Reply {
    @Id @GeneratedValue
    private Long id;
    private String content;
    private long created;

    @Relationship(type = "USER_REPLY", direction = Relationship.UNDIRECTED)
    private User user;

    @Relationship(type = "LIKES", direction = Relationship.UNDIRECTED)
    private Set<User> likes;

    @Relationship(type = "POST_REPLY", direction = Relationship.UNDIRECTED)
    private Post post;

    public Reply() {
        created = System.currentTimeMillis();
        likes = new HashSet<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<User> getLikes() {
        return likes;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    @Override
    public String toString() {
        return id + " " + content + " " + user.getUsername() + " " + post.getTitle() + " " + likes.size();
    }
}
