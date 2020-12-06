package com.agh.riceitapi.validator;

import com.agh.riceitapi.exception.WrongDateFormatException;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateValidator {

    public static boolean isValid(String dateStr) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try{
            dateTimeFormatter.parse(dateStr);
        } catch (DateTimeException ex){
            return false;
        }
        return true;
    }

    public static LocalDate parseStrToLocalDate(String dateStr){
        if (DateValidator.isValid(dateStr)){
            return LocalDate.parse(dateStr);
        } else throw new WrongDateFormatException("Wrong date format (should be yyyy-MM-dd).");
    }

}
