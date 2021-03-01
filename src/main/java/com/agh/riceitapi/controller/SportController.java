package com.agh.riceitapi.controller;


import com.agh.riceitapi.dto.*;
import com.agh.riceitapi.log.LogExecutionTime;
import com.agh.riceitapi.model.Sport;
import com.agh.riceitapi.security.CurrentUser;
import com.agh.riceitapi.security.UserPrincipal;
import com.agh.riceitapi.service.SportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
public class SportController {

    @Autowired
    private SportService sportService;

    @PostMapping("/sports")
    @LogExecutionTime
    public ResponseEntity<String> addSport(@CurrentUser UserPrincipal currentUser, @RequestBody SportAddDTO sportAddDTO){
        sportService.addSport(currentUser.getId(), sportAddDTO);

        String str = "";
        if (sportAddDTO.getKcalBurnt() < 0){
            str = "automatically";
        } else str = "manually";

        return new ResponseEntity("Sport added successfully.", HttpStatus.OK);
    }

    @DeleteMapping("/sports/{sportId}")
    @LogExecutionTime
    public ResponseEntity<String> removeSport(@CurrentUser UserPrincipal currentUser, @PathVariable Long sportId){
        sportService.removeSport(currentUser.getId(), sportId);
        return new ResponseEntity("Sport successfully removed.", HttpStatus.OK);
    }

    @PutMapping("/sports/{sportId}")
    @LogExecutionTime
    public ResponseEntity<String> updateSport(@CurrentUser UserPrincipal currentUser, @PathVariable Long sportId, @RequestBody SportDTO sportDTO) throws IOException {
        sportService.updateSport(currentUser.getId(), sportId, sportDTO);
        return new ResponseEntity("Sport updated successfully", HttpStatus.OK);
    }

    @PostMapping("/sports/all")
    @LogExecutionTime
    public ResponseEntity<SportsDTO> getSports(@CurrentUser UserPrincipal currentUser, @RequestBody DateDTO dateDTO){
        SportsDTO sportsDTO = sportService.getSports(currentUser.getId(), dateDTO);
        return new ResponseEntity(sportsDTO, HttpStatus.OK);
    }

    @GetMapping("/sports/{sportId}")
    @LogExecutionTime
    public ResponseEntity<Sport> getSport(@CurrentUser UserPrincipal currentUser, @PathVariable Long sportId){
        Sport sport = sportService.getSport(currentUser.getId(), sportId);
        return new ResponseEntity(sport, HttpStatus.OK);
    }
}
