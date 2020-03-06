package com.finkicommunity.domain;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashSet;
import java.util.Set;


@NodeEntity
public class User {
    @Id @GeneratedValue
    private Long id;
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private char gender;
    private long birthdate;
    private long created;

    @Relationship(type = "HAS_ROLE")
    private Set<Role> roles;

    @Relationship(type = "FOLLOWING")
    private Set<User> following;

//    @Relationship(type = "FOLLOWER", direction = Relationship.INCOMING)
//    private Set<User> followers;

    private double rating;

    private String profilePictureUrl;

    private boolean isBlocked;

    public User() {
        created = LocalDateTime.now().toInstant(ZoneOffset.UTC).toEpochMilli();
        // created = System.currentTimeMillis();
        rating = 0.0;
        isBlocked = false;
        following = new HashSet<>();
    }

    public User(String username, String password, Set<Role> roles) {
        this.username = username;
        this.password = password;
        this.roles = roles;
        created = LocalDateTime.now().toInstant(ZoneOffset.UTC).toEpochMilli();
        // created = System.currentTimeMillis();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public long getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(long birthdate) {
        this.birthdate = birthdate;
    }

    public long getCreated() {
        return created;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Set<User> getFollowing() {
        return following;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }

    public void setProfilePictureUrl(String profilePictureUrl) {
        this.profilePictureUrl = profilePictureUrl;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }

    @Override
    public String toString() {
        String value =  "User: " + username + " " + firstName + " " + lastName + " Roles: ";
        for(Role role: roles){
            value += role + " ";
        }
        return value;
    }
}
