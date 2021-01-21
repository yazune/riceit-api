package com.agh.riceitapi.exception.handler;

import com.agh.riceitapi.dto.ErrorResponse;
import com.agh.riceitapi.exception.*;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private final Log log = LogFactory.getLog(getClass());


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAllExceptions(Exception ex){
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        ErrorResponse errorResponse = new ErrorResponse("Unknown exception", details);
        log.warn(ex);
        return new ResponseEntity(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(InternalServerException.class)
    public ResponseEntity<ErrorResponse> handleInternalServerException(Exception ex){
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        ErrorResponse errorResponse = new ErrorResponse("Internal Server Exception", details);
        log.warn(ex);
        return new ResponseEntity(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request){
        List<String> details = new ArrayList<>();
        for (ObjectError error : ex.getBindingResult().getAllErrors()){
            details.add(error.getDefaultMessage());
        }
        ErrorResponse errorResponse = new ErrorResponse("Validation failed.", details);
        return new ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MealNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleMealNotFoundException(MealNotFoundException ex){
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        ErrorResponse errorResponse = new ErrorResponse("Meal not found.", details);
        log.warn(ex);
        return new ResponseEntity(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(FoodNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleFoodNotFoundException(FoodNotFoundException ex){
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        ErrorResponse errorResponse = new ErrorResponse("Food not found.", details);
        log.warn(ex);
        return new ResponseEntity(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PermissionDeniedException.class)
    public ResponseEntity<ErrorResponse> handlePermissionDeniedException(PermissionDeniedException ex){
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        ErrorResponse errorResponse = new ErrorResponse("Permission denied.", details);
        log.warn(ex);
        return new ResponseEntity(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException ex){
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        ErrorResponse errorResponse = new ErrorResponse("User not found.", details);
        log.warn(ex);
        return new ResponseEntity(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(WrongDateFormatException.class)
    public ResponseEntity<ErrorResponse> handleWrongDateFormatException(WrongDateFormatException ex){
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        ErrorResponse errorResponse = new ErrorResponse("Wrong date format.", details);
        log.warn(ex);
        return new ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SportNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleSportNotFoundException(SportNotFoundException ex){
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        ErrorResponse errorResponse = new ErrorResponse("Sport not found.", details);
        log.warn(ex);
        return new ResponseEntity(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleUserAlreadyExistsException(UserAlreadyExistsException ex){
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        ErrorResponse errorResponse = new ErrorResponse("User already exists.", details);
        log.warn(ex);
        return new ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleEmailAlreadyExistsException(EmailAlreadyExistsException ex){
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        ErrorResponse errorResponse = new ErrorResponse("Email already exists.", details);
        log.warn(ex);
        return new ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RegisterException.class)
    public ResponseEntity<ErrorResponse> handleRegisterException(RegisterException ex){
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        ErrorResponse errorResponse = new ErrorResponse("Register Exception.", details);
        log.warn(ex);
        return new ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserDetailsNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserDetailsNotFoundException(UserDetailsNotFoundException ex){
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        ErrorResponse errorResponse = new ErrorResponse("User details not found.", details);
        log.warn(ex);
        return new ResponseEntity(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DayNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleDayNotFoundException(DayNotFoundException ex){
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        ErrorResponse errorResponse = new ErrorResponse("Day not found.", details);
        log.warn(ex);
        return new ResponseEntity(errorResponse, HttpStatus.NOT_FOUND);
    }

}
