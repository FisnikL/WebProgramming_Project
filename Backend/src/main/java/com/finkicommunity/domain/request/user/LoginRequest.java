package com.finkicommunity.domain.request.user;

import javax.validation.constraints.NotNull;

public class LoginRequest {
    @NotNull
    public String username;
    @NotNull
    public String password;

    @Override
    public String toString() {
        return "LoginRequest{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
