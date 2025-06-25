package com.akashkumar.unu.ExceptionHandling.AuthenticationException;

public class UserFound extends RuntimeException{
    public UserFound(String message) {
        super(message);
    }
}
