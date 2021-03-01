package com.agh.riceitapi.controller;

import com.agh.riceitapi.dto.*;
import com.agh.riceitapi.log.LogExecutionTime;
import com.agh.riceitapi.security.CurrentUser;
import com.agh.riceitapi.security.UserPrincipal;
import com.agh.riceitapi.service.UserSettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class UserSettingsController {

    @Autowired
    private UserSettingsService userSettingsService;

    @GetMapping("/users/settings")
    @LogExecutionTime
    public ResponseEntity<UserSettingsDTO> getUserSettings(@CurrentUser UserPrincipal currentUser){
        UserSettingsDTO dto = userSettingsService.getUserSettings(currentUser.getId());
        return new ResponseEntity(dto, HttpStatus.OK);
    }

    @PutMapping("/users/settings")
    @LogExecutionTime
    public ResponseEntity<String> updateUserSettings(@CurrentUser UserPrincipal currentUser, @RequestBody UserSettingsDTO dto){
        userSettingsService.updateUserSettings(currentUser.getId(), dto);
        return new ResponseEntity("User settings successfully updated", HttpStatus.OK);
    }
}
