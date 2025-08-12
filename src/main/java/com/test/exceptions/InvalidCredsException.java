package com.test.exceptions;

public class InvalidCredsException extends RuntimeException{
    public InvalidCredsException(String message)
    {
        super(message);
    }
}
