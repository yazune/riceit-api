package com.agh.riceitapi.exception;

public class WrongDateFormatException extends RuntimeException{

    public WrongDateFormatException(String s){
        super(s);
    }
}
