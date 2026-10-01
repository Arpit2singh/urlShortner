package com.arpit.shortlink.GlobalExceptionHandling.ExceptionHandler;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import com.arpit.shortlink.GlobalExceptionHandling.urlNotFoundException;

@RestControllerAdvice
public class UrlNotFoundException {
    @ExceptionHandler(urlNotFoundException.class)
    public ResponseEntity<String> handleUrlNotFoundException(urlNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
}
