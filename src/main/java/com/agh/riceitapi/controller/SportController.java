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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

import static java.lang.String.format;

@RestController
public class SportController {

    @Autowired
    private SportService sportService;

    private final Log log = LogFactory.getLog(getClass());

    @PostMapping("/sports/addMan")
    public ResponseEntity<Sport> addSportMan(@CurrentUser UserPrincipal currentUser, @RequestBody AddSportManDTO addSportManDTO){
        long startTime = System.nanoTime();

        sportService.addSportMan(currentUser.getId(), addSportManDTO);

        long elapsedTime = System.nanoTime() - startTime;
        log.info(format("%s in: %.10f [s]", "adding new sport manually", (elapsedTime/Math.pow(10,9))));

        return new ResponseEntity("Sport added successfully.", HttpStatus.OK);
    }

    @PostMapping("/sports/addAuto")
    public ResponseEntity<Sport> addSportAuto(@CurrentUser UserPrincipal currentUser, @RequestBody AddSportAutoDTO addSportAutoDTO){
        long startTime = System.nanoTime();

        sportService.addSportAuto(currentUser.getId(), addSportAutoDTO);

        long elapsedTime = System.nanoTime() - startTime;
        log.info(format("%s in: %.10f [s]", "adding new sport automatically", (elapsedTime/Math.pow(10,9))));

        return new ResponseEntity("Sport added successfully.", HttpStatus.OK);
    }

    @PostMapping("/sports/remove")
    public ResponseEntity<String> removeSport(@CurrentUser UserPrincipal currentUser, @RequestBody RemoveSportDTO removeSportDTO){
        long startTime = System.nanoTime();

        sportService.removeSport(currentUser.getId(), removeSportDTO);

        long elapsedTime = System.nanoTime() - startTime;
        log.info(format("%s in: %.10f [s]", "removing sport", (elapsedTime/Math.pow(10,9))));

        return new ResponseEntity("Sport successfully removed.", HttpStatus.OK);
    }

    @PostMapping("/sports/update")
    public ResponseEntity<Sport> updateSport(@CurrentUser UserPrincipal currentUser, @RequestBody UpdateSportDTO updateSportDTO) throws IOException {
        long startTime = System.nanoTime();

        sportService.updateSport(currentUser.getId(), updateSportDTO);

        long elapsedTime = System.nanoTime() - startTime;
        log.info(format("%s in: %.10f [s]", "updating sport", (elapsedTime/Math.pow(10,9))));

        return new ResponseEntity("Sport updated successfully", HttpStatus.OK);
    }

    @PostMapping("/sports/showAll")
    public ResponseEntity<AllSportsDTO> showAllSports(@CurrentUser UserPrincipal currentUser, @RequestBody DateDTO dateDTO){
        long startTime = System.nanoTime();

        AllSportsDTO allSportsDTO = sportService.showAllSports(currentUser.getId(), dateDTO);

        long elapsedTime = System.nanoTime() - startTime;
        log.info(format("%s in: %.10f [s]", "showing all sports", (elapsedTime/Math.pow(10,9))));

        return new ResponseEntity(allSportsDTO, HttpStatus.OK);
    }

    @PostMapping("/sports/get")
    public ResponseEntity<Sport> getSport(@CurrentUser UserPrincipal currentUser, @RequestBody GetSportDTO getSportDTO){
        long startTime = System.nanoTime();

        Sport sport = sportService.getSport(currentUser.getId(), getSportDTO);

        long elapsedTime = System.nanoTime() - startTime;
        log.info(format("%s in: %.10f [s]", "getting sport", (elapsedTime/Math.pow(10,9))));

        return new ResponseEntity(sport, HttpStatus.OK);
    }
}
