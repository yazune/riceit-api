package com.agh.riceitapi.controller;

import com.agh.riceitapi.dto.*;
import com.agh.riceitapi.exception.RegisterException;
import com.agh.riceitapi.log.LogExecutionTime;
import com.agh.riceitapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/auth/register")
    @LogExecutionTime
    public @ResponseBody ResponseEntity<UsernameDTO> register(@Valid @RequestBody RegisterDTO registerDTO) throws RegisterException {
       UsernameDTO usernameDTO = this.userService.createUser(registerDTO);
       return new ResponseEntity(usernameDTO, HttpStatus.OK);
    }

    @DeleteMapping("/users/{userId}")
    @LogExecutionTime
    public ResponseEntity<String> removeUser(@PathVariable Long userId){
        userService.removeUser(userId);
        return ResponseEntity.ok("User has been successfully removed!");
    }

    @PostMapping("/auth/existsByUsername")
    @LogExecutionTime
    public @ResponseBody ResponseEntity<BooleanDTO> existsByUsername(@Valid @RequestBody ExistsUsernameDTO existsUsernameDTO){
        BooleanDTO booleanDTO = this.userService.existsByUsername(existsUsernameDTO);
        return new ResponseEntity(booleanDTO, HttpStatus.OK);

    }

    @PostMapping("/auth/existsByEmail")
    @LogExecutionTime
    public @ResponseBody ResponseEntity<BooleanDTO> existsByEmail(@Valid @RequestBody ExistsEmailDTO existsEmailDTO){
        BooleanDTO booleanDTO = this.userService.existsByEmail(existsEmailDTO);
        return new ResponseEntity(booleanDTO, HttpStatus.OK);
    }

    @GetMapping("/test/hello")
    @LogExecutionTime
    public String hello(){
        return "hello man!";
    }
}