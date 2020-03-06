package com.finkicommunity.exception.user;

public class SameUsernameFollowingException extends RuntimeException {

    public SameUsernameFollowingException(String message){
        super(message);
    }
}
