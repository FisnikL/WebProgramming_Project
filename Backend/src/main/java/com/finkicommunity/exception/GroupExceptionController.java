package com.finkicommunity.exception;

import com.finkicommunity.exception.group.GroupCodeAlreadyExistsException;
import com.finkicommunity.exception.group.GroupDoesntExistException;
import com.finkicommunity.exception.group.GroupNameAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GroupExceptionController {

    @ExceptionHandler(value = GroupCodeAlreadyExistsException.class)
    public ResponseEntity<String> groupCodeAlreadyExists(GroupCodeAlreadyExistsException e){
        return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = GroupNameAlreadyExistsException.class)
    public ResponseEntity<String> groupNameAlreadyExists(GroupNameAlreadyExistsException e){
        return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = GroupDoesntExistException.class)
    public ResponseEntity<String> groupDoesntExist(GroupDoesntExistException e){
        return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
