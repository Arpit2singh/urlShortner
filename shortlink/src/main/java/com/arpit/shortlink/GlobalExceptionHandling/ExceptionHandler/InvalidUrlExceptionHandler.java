package com.arpit.shortlink.GlobalExceptionHandling.ExceptionHandler;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.arpit.shortlink.GlobalExceptionHandling.InvalidUrlException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
@RestControllerAdvice 
public class InvalidUrlExceptionHandler {
    @ExceptionHandler (InvalidUrlException.class) 
    public ResponseEntity<String> handleIllegalArgumentException(InvalidUrlException ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}   
