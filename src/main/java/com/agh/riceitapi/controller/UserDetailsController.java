package com.agh.riceitapi.controller;

import com.agh.riceitapi.dto.UserDetailsGetDTO;
import com.agh.riceitapi.dto.UserDetailsDTO;
import com.agh.riceitapi.security.CurrentUser;
import com.agh.riceitapi.security.UserPrincipal;
import com.agh.riceitapi.service.UserDetailsService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static java.lang.String.format;

@RestController
public class UserDetailsController {

    @Autowired
    private UserDetailsService userDetailsService;

    private final Log log = LogFactory.getLog(getClass());

    @GetMapping("/users/details")
    public ResponseEntity<UserDetailsGetDTO> getUserDetails(@CurrentUser UserPrincipal currentUser){
        long startTime = System.nanoTime();

        UserDetailsGetDTO userDetailsGetDTO = userDetailsService.getUserDetails(currentUser);

        long elapsedTime = System.nanoTime() - startTime;
        log.info(format("%s in: %.10f [s]", "getting user details", (elapsedTime/Math.pow(10,9))));

        return new ResponseEntity(userDetailsGetDTO, HttpStatus.OK);
    }

    @PutMapping("/users/details")
    public ResponseEntity<String> updateUserDetails(@CurrentUser UserPrincipal currentUser, @RequestBody @Valid UserDetailsDTO userDetailsDTO){
        long startTime = System.nanoTime();

        userDetailsService.updateUserDetails(currentUser.getId(), userDetailsDTO);

        long elapsedTime = System.nanoTime() - startTime;
        log.info(format("%s in: %.10f [s]", "updating user details", (elapsedTime/Math.pow(10,9))));

        return new ResponseEntity("User details successfully updated.", HttpStatus.OK);
    }

}
