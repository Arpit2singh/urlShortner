package com.arpit.shortlink.GlobalExceptionHandling;


public class InvalidUrlException extends RuntimeException{
    public InvalidUrlException(String message){
        super(message) ; 
    }
}
