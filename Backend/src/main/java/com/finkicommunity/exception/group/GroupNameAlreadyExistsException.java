package com.finkicommunity.exception.group;

public class GroupNameAlreadyExistsException extends RuntimeException {

    public GroupNameAlreadyExistsException(String message){
        super(message);
    }
}
