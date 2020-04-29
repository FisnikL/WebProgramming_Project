package com.finkicommunity.domain.response;

import com.finkicommunity.domain.Role;

import java.util.Set;

public class UserResponse {
    public Long id;
    public String username;
    public String password;
    public String email;
    public String firstName;
    public String lastName;
    public char gender;
    public long birthdate;
    public long created;
    public int numFollowings;
    public int numFollowers;
    public double rating;
    public String profilePictureUrl;
    public boolean isBlocked;
    public Set<Role> roles;
}
