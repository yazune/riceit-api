package com.agh.riceitapi.exception;

public class FoodNotFoundException extends RuntimeException {

    public FoodNotFoundException(String s) {
        super(s);
    }
}
