package com.agh.riceitapi.exception;

public class PermissionDeniedException extends RuntimeException {
    public PermissionDeniedException(String s) {
        super(s);
    }
}
