package com.finkicommunity.exception;

import com.finkicommunity.exception.reply.ReplyNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ReplyExceptionController {

    @ExceptionHandler(value = ReplyNotFoundException.class)
    public ResponseEntity<String> replyNotFoundException(ReplyNotFoundException e){
        return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
