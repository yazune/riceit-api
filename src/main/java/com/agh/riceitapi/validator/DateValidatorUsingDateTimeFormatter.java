package com.agh.riceitapi.validator;

import java.time.DateTimeException;
import java.time.format.DateTimeFormatter;

public class DateValidatorUsingDateTimeFormatter implements DateValidator {

    private DateTimeFormatter dateTimeFormatter;

    public DateValidatorUsingDateTimeFormatter(DateTimeFormatter dateTimeFormatter){
        this.dateTimeFormatter = dateTimeFormatter;
    }

    @Override
    public boolean isValid(String dateStr) {
        try{
            this.dateTimeFormatter.parse(dateStr);
        } catch (DateTimeException ex){
            return false;
        }
        return true;
    }
}
