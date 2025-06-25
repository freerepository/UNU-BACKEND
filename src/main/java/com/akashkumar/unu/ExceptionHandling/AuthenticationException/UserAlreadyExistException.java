package com.akashkumar.unu.ExceptionHandling.AuthenticationException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
//Handle in -> Controller with try-catch -> catch(UserAlreadyExistException ex);

public class UserAlreadyExistException extends RuntimeException{
    public UserAlreadyExistException(String message) {
        super(message);
    }
}
