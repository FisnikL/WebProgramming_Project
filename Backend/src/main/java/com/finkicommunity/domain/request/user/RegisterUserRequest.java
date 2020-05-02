package com.finkicommunity.domain.request.user;

import javax.validation.constraints.NotNull;

public class RegisterUserRequest {
    @NotNull
    public String username;
    @NotNull
    public String firstName;
    @NotNull
    public String lastName;
    @NotNull
    public String email;
    @NotNull
    public String password;
    @NotNull
    public String confirmPassword;
    @NotNull
    public char gender;

    @Override
    public String toString() {
        return "RegisterUserRequest{" +
                "username='" + username + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", confirmPassword='" + confirmPassword + '\'' +
                ", gender=" + gender +
                '}';
    }
}
