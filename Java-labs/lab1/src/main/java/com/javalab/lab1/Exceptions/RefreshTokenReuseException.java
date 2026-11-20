package com.javalab.lab1.Exceptions;

public class RefreshTokenReuseException extends InvalidRefreshToken{
    public RefreshTokenReuseException(String message)
    {
        super(message);
    }
}
