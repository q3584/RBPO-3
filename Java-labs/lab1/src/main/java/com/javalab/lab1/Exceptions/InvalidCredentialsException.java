package com.javalab.lab1.Exceptions;

public class InvalidCredentialsException extends RuntimeException{
    public InvalidCredentialsException(String message)
    {
        super(message);
    }
}
