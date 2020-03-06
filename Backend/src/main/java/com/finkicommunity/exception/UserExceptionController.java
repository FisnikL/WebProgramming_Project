package com.finkicommunity.exception;

import com.finkicommunity.exception.user.SameUsernameFollowingException;
import com.finkicommunity.exception.user.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UserExceptionController {

    @ExceptionHandler(value = UserNotFoundException.class)
    public ResponseEntity<String> userNotFoundException(UserNotFoundException e){
        return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = SameUsernameFollowingException.class)
    public ResponseEntity<String> sameUsernameFollowing(SameUsernameFollowingException e){
        return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
