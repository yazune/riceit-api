package com.agh.riceitapi.controller;


import com.agh.riceitapi.dto.DateDTO;
import com.agh.riceitapi.log.LogExecutionTime;
import com.agh.riceitapi.model.Day;
import com.agh.riceitapi.security.CurrentUser;
import com.agh.riceitapi.security.UserPrincipal;
import com.agh.riceitapi.service.DayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class DayController {

    @Autowired
    private DayService dayService;

    @PostMapping("/days")
    @LogExecutionTime
    public ResponseEntity<Day> getDay(@CurrentUser UserPrincipal currentUser, @RequestBody @Valid DateDTO dateDTO){
        Day day = dayService.getDay(currentUser.getId(),dateDTO);
        return new ResponseEntity(day, HttpStatus.OK);
    }

    @GetMapping("/days/last")
    @LogExecutionTime
    public ResponseEntity<Day> getLastDay(@CurrentUser UserPrincipal currentUser){
        Day day = dayService.getLastDay(currentUser.getId());
        return new ResponseEntity(day, HttpStatus.OK);
    }


}
