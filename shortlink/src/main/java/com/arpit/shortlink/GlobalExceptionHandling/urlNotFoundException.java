package com.arpit.shortlink.GlobalExceptionHandling;

public class urlNotFoundException extends RuntimeException {
    public urlNotFoundException(String message){
        super(message) ; 
    }
}
