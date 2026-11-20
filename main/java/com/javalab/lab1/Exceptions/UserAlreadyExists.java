package com.javalab.lab1.Exceptions;

public class UserAlreadyExists extends RuntimeException{
    public UserAlreadyExists(String message)
    {
        super(message);
    }
}