package com.finkicommunity.exception.group;

public class GroupDoesntExistException extends RuntimeException {

    public GroupDoesntExistException(String message){
        super(message);
    }
}
