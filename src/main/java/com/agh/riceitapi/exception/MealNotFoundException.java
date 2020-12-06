package com.agh.riceitapi.exception;

public class MealNotFoundException extends RuntimeException{
    public MealNotFoundException(String s){
        super(s);
    }
}
