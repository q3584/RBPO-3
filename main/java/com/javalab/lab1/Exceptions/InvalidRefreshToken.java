package com.javalab.lab1.Exceptions;

public class InvalidRefreshToken extends RuntimeException{
    public InvalidRefreshToken(String message)
    {
        super(message);
    }
}