package com.agh.riceitapi.exception;

public class UserDetailsNotFoundException extends RuntimeException {
    public UserDetailsNotFoundException(String s) {
        super(s);
    }
}
