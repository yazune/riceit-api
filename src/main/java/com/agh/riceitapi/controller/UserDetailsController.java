package com.agh.riceitapi.controller;

import com.agh.riceitapi.dto.UserDetailsGetDTO;
import com.agh.riceitapi.dto.UserDetailsDTO;
import com.agh.riceitapi.log.LogExecutionTime;
import com.agh.riceitapi.security.CurrentUser;
import com.agh.riceitapi.security.UserPrincipal;
import com.agh.riceitapi.service.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class UserDetailsController {

    @Autowired
    private UserDetailsService userDetailsService;

    @GetMapping("/users/details")
    @LogExecutionTime
    public ResponseEntity<UserDetailsGetDTO> getUserDetails(@CurrentUser UserPrincipal currentUser){
        UserDetailsGetDTO userDetailsGetDTO = userDetailsService.getUserDetails(currentUser);
        return new ResponseEntity(userDetailsGetDTO, HttpStatus.OK);
    }

    @PutMapping("/users/details")
    @LogExecutionTime
    public ResponseEntity<String> updateUserDetails(@CurrentUser UserPrincipal currentUser, @RequestBody @Valid UserDetailsDTO userDetailsDTO){
        userDetailsService.updateUserDetails(currentUser.getId(), userDetailsDTO);
        return new ResponseEntity("User details successfully updated.", HttpStatus.OK);
    }

}
