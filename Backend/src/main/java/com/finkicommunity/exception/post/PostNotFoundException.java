package com.finkicommunity.exception.post;

public class PostNotFoundException extends RuntimeException {

    public PostNotFoundException(String message){
        super(message);
    }
}
