package com.finkicommunity.exception;

import com.finkicommunity.exception.post.PostNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class PostExceptionController {

    @ExceptionHandler(value = PostNotFoundException.class)
    public ResponseEntity<String> postNotFoundException(PostNotFoundException e){
        return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
