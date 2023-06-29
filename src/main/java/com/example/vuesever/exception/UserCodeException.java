package com.example.vuesever.exception;

import org.springframework.security.core.AuthenticationException;

public class UserCodeException extends AuthenticationException {
    public UserCodeException(String message) {
        super(message);
    }
}
