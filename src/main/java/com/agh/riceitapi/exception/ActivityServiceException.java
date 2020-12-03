package com.agh.riceitapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ActivityServiceException extends RuntimeException {

    public ActivityServiceException(String message) {
        super(message);
    }
}
