package com.agh.riceitapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class MealServiceException extends RuntimeException{

    public MealServiceException(String message){
        super(message);
    }
}
