package com.agh.riceitapi.controller;


import com.agh.riceitapi.dto.*;
import com.agh.riceitapi.model.UserSettings;
import com.agh.riceitapi.security.CurrentUser;
import com.agh.riceitapi.security.UserPrincipal;
import com.agh.riceitapi.service.UserSettingsService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static java.lang.String.format;

@Controller
public class UserSettingsController {

    @Autowired
    private UserSettingsService userSettingsService;

    private final Log log = LogFactory.getLog(getClass());


    @GetMapping("/users/settings")
    public ResponseEntity<UserSettingsDTO> getUserSettings(@CurrentUser UserPrincipal currentUser){
        long startTime = System.nanoTime();

        UserSettingsDTO dto = userSettingsService.getUserSettings(currentUser.getId());

        long elapsedTime = System.nanoTime() - startTime;
        log.info(format("%s in: %.10f [s]", "getting user settings", (elapsedTime/Math.pow(10,9))));

        return new ResponseEntity(dto, HttpStatus.OK);
    }

    @PutMapping("/users/settings")
    public ResponseEntity<String> updateUserSettings(@CurrentUser UserPrincipal currentUser, @RequestBody UserSettingsDTO dto){
        long startTime = System.nanoTime();

        userSettingsService.updateUserSettings(currentUser.getId(), dto);

        long elapsedTime = System.nanoTime() - startTime;
        log.info(format("%s in: %.10f [s]", "updating user settings", (elapsedTime/Math.pow(10,9))));

        return new ResponseEntity("User settings successfully updated", HttpStatus.OK);
    }
}
