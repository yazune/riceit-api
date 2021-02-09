package com.agh.riceitapi.controller;


import com.agh.riceitapi.dto.*;
import com.agh.riceitapi.model.Sport;
import com.agh.riceitapi.security.CurrentUser;
import com.agh.riceitapi.security.UserPrincipal;
import com.agh.riceitapi.service.SportService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

import static java.lang.String.format;

@RestController
public class SportController {

    @Autowired
    private SportService sportService;

    private final Log log = LogFactory.getLog(getClass());

    @PostMapping("/sports")
    public ResponseEntity<Sport> addSport(@CurrentUser UserPrincipal currentUser, @RequestBody SportAddDTO sportAddDTO){
        long startTime = System.nanoTime();

        sportService.addSport(currentUser.getId(), sportAddDTO);

        long elapsedTime = System.nanoTime() - startTime;

        String str = "";
        if (sportAddDTO.getKcalBurnt() < 0){
            str = "automatically";
        } else str = "manually";

        log.info(format("%s in: %.10f [s]", "adding new sport " +str, (elapsedTime/Math.pow(10,9))));

        return new ResponseEntity("Sport added successfully.", HttpStatus.OK);
    }

    @DeleteMapping("/sports/{sportId}")
    public ResponseEntity<String> removeSport(@CurrentUser UserPrincipal currentUser, @PathVariable Long sportId){
        long startTime = System.nanoTime();

        sportService.removeSport(currentUser.getId(), sportId);

        long elapsedTime = System.nanoTime() - startTime;
        log.info(format("%s in: %.10f [s]", "removing sport", (elapsedTime/Math.pow(10,9))));

        return new ResponseEntity("Sport successfully removed.", HttpStatus.OK);
    }

    @PutMapping("/sports/{sportId}")
    public ResponseEntity<Sport> updateSport(@CurrentUser UserPrincipal currentUser, @PathVariable Long sportId, @RequestBody SportUpdateDTO sportUpdateDTO) throws IOException {
        long startTime = System.nanoTime();

        sportService.updateSport(currentUser.getId(), sportId, sportUpdateDTO);

        long elapsedTime = System.nanoTime() - startTime;
        log.info(format("%s in: %.10f [s]", "updating sport", (elapsedTime/Math.pow(10,9))));

        return new ResponseEntity("Sport updated successfully", HttpStatus.OK);
    }

    @PostMapping("/sports/all")
    public ResponseEntity<List<Sport>> getSports(@CurrentUser UserPrincipal currentUser, @RequestBody DateDTO dateDTO){
        long startTime = System.nanoTime();

        SportsDTO sportsDTO = sportService.getSports(currentUser.getId(), dateDTO);

        long elapsedTime = System.nanoTime() - startTime;
        log.info(format("%s in: %.10f [s]", "getting all sports", (elapsedTime/Math.pow(10,9))));

        return new ResponseEntity(sportsDTO, HttpStatus.OK);
    }

    @GetMapping("/sports/{sportId}")
    public ResponseEntity<Sport> getSport(@CurrentUser UserPrincipal currentUser, @PathVariable Long sportId){
        long startTime = System.nanoTime();

        Sport sport = sportService.getSport(currentUser.getId(), sportId);

        long elapsedTime = System.nanoTime() - startTime;
        log.info(format("%s in: %.10f [s]", "getting sport", (elapsedTime/Math.pow(10,9))));

        return new ResponseEntity(sport, HttpStatus.OK);
    }
}
