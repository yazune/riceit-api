package com.agh.riceitapi.exception.handler;

import com.agh.riceitapi.controller.UserController;
import com.agh.riceitapi.exception.RegisterException;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.IOException;

@ControllerAdvice(assignableTypes = UserController.class)
public class UserControllerExceptionHandler {

    private final Log log = LogFactory.getLog(getClass());

    @ExceptionHandler(RegisterException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleRegisterException(RegisterException ex){
        return ex.getMessage();
    }

//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    @ResponseBody
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public String handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
//        return ex.getMessage();
//    }
//
//    @ExceptionHandler(IOException.class)
//    @ResponseBody
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public String handleIOException(IOException ex) {
//        return ex.getMessage();
//    }
//
//    @ExceptionHandler(Exception.class)
//    @ResponseBody
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    public String handleUnknownException(Exception ex) {
//        return ex.getMessage();
//    }


}
