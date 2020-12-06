package com.agh.riceitapi.controller;

import com.agh.riceitapi.dto.UserDetailsDTO;
import com.agh.riceitapi.model.UserDetails;
import com.agh.riceitapi.security.CurrentUser;
import com.agh.riceitapi.security.UserPrincipal;
import com.agh.riceitapi.service.UserDetailsService;
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
public class UserDetailsController {

    @Autowired
    private UserDetailsService userDetailsService;

    private final Log log = LogFactory.getLog(getClass());


    @GetMapping("/user/checkDetails")
    public Boolean existsByUserId(@CurrentUser UserPrincipal currentUser){
        long startTime = System.nanoTime();

        boolean areDetailsCreated = userDetailsService.areUserDetailsCreated(currentUser.getId());

        long elapsedTime = System.nanoTime() - startTime;
        log.info(format("%s in: %.10f [s]", "checking if user details exists", (elapsedTime/Math.pow(10,9))));

        return areDetailsCreated;
    }

    @GetMapping("/user/getDetails")
    public ResponseEntity<UserDetails> getUserDetails(@CurrentUser UserPrincipal currentUser){
        long startTime = System.nanoTime();

        UserDetails userDetails = userDetailsService.getUserDetails(currentUser.getId());

        long elapsedTime = System.nanoTime() - startTime;
        log.info(format("%s in: %.10f [s]", "getting user details", (elapsedTime/Math.pow(10,9))));

        return new ResponseEntity(userDetails, HttpStatus.OK);
    }


    @PostMapping("/user/createDetails")
    public ResponseEntity<String> createUserDetails(@CurrentUser UserPrincipal currentUser, @RequestBody @Valid UserDetailsDTO userDetailsDTO){
        long startTime = System.nanoTime();

        userDetailsService.createUserDetails(currentUser.getId(), userDetailsDTO);

        long elapsedTime = System.nanoTime() - startTime;
        log.info(format("%s in: %.10f [s]", "creating user details", (elapsedTime/Math.pow(10,9))));

        return new ResponseEntity("User details successfully created.", HttpStatus.OK);
    }

    @PostMapping("/user/updateDetails")
    public ResponseEntity<String> updateUserDetails(@CurrentUser UserPrincipal currentUser, @RequestBody @Valid UserDetailsDTO userDetailsDTO){
        long startTime = System.nanoTime();

        userDetailsService.updateUserDetails(currentUser.getId(), userDetailsDTO);

        long elapsedTime = System.nanoTime() - startTime;
        log.info(format("%s in: %.10f [s]", "updating user details", (elapsedTime/Math.pow(10,9))));

        return new ResponseEntity("User details successfully updated.", HttpStatus.OK);
    }

}
