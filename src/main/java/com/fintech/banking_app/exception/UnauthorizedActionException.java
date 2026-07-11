package com.fintech.banking_app.exception;

public class UnauthorizedActionException 
        extends RuntimeException {

    public UnauthorizedActionException(String message){
        super(message);
    }
}