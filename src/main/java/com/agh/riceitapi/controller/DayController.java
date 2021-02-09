package com.agh.riceitapi.controller;


import com.agh.riceitapi.dto.DateDTO;
import com.agh.riceitapi.model.Day;
import com.agh.riceitapi.security.CurrentUser;
import com.agh.riceitapi.security.UserPrincipal;
import com.agh.riceitapi.service.DayService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static java.lang.String.format;

@RestController
public class DayController {


    @Autowired
    private DayService dayService;

    private final Log log = LogFactory.getLog(getClass());


    @PostMapping("/days")
    public ResponseEntity<Day> getDay(@CurrentUser UserPrincipal currentUser, @RequestBody @Valid DateDTO dateDTO){
        long startTime = System.nanoTime();

        Day day = dayService.getDay(currentUser.getId(),dateDTO);

        long elapsedTime = System.nanoTime() - startTime;
        log.info(format("%s in: %.10f [s]", "getting a day", (elapsedTime/Math.pow(10,9))));

        return new ResponseEntity(day, HttpStatus.OK);
    }

    @GetMapping("/days/last")
    public ResponseEntity<Day> getLastDay(@CurrentUser UserPrincipal currentUser){
        long startTime = System.nanoTime();

        Day day = dayService.getLastDay(currentUser.getId());

        long elapsedTime = System.nanoTime() - startTime;
        log.info(format("%s in: %.10f [s]", "getting last day", (elapsedTime/Math.pow(10,9))));

        return new ResponseEntity(day, HttpStatus.OK);
    }


}
