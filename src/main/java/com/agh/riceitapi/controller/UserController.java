package com.agh.riceitapi.controller;

import com.agh.riceitapi.dto.DeleteUserDTO;
import com.agh.riceitapi.dto.ExistsEmailDTO;
import com.agh.riceitapi.dto.ExistsUsernameDTO;
import com.agh.riceitapi.dto.RegisterDTO;
import com.agh.riceitapi.exception.EmailAlreadyExistsException;
import com.agh.riceitapi.exception.RegisterException;
import com.agh.riceitapi.exception.UserAlreadyExistsException;
import com.agh.riceitapi.model.User;
import com.agh.riceitapi.service.UserService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static java.lang.String.format;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    private final Log log = LogFactory.getLog(getClass());

    @PostMapping("/auth/register")
    public @ResponseBody ResponseEntity<User> register(@Valid @RequestBody RegisterDTO registerDTO) throws RegisterException {

       long startTime = System.nanoTime();

       User user = this.userService.createUser(registerDTO);
       long elapsedTime = System.nanoTime() - startTime;
       log.info(format("%s: %.10f [s]", "register", (elapsedTime/Math.pow(10,9))));
       return new ResponseEntity(user, HttpStatus.OK);
    }

    @PostMapping("/user/delete")
    public ResponseEntity deleteUser(@RequestBody DeleteUserDTO deleteUserDTO){
        userService.deleteUser(deleteUserDTO);
        return ResponseEntity.ok("Deleted!");
    }

    @PostMapping("/existsByUsername")
    public @ResponseBody ResponseEntity<Boolean> existsByUsername(@Valid @RequestBody ExistsUsernameDTO existsUsernameDTO){

        long startTime = System.nanoTime();

        Boolean check = this.userService.existsByUsername(existsUsernameDTO);
        long elapsedTime = System.nanoTime() - startTime;
        log.info(format("%s: %.10f [s]", "register", (elapsedTime/Math.pow(10,9))));
        return new ResponseEntity(check, HttpStatus.OK);

    }

    @PostMapping("/existsByEmail")
    public @ResponseBody ResponseEntity<Boolean> existsByEmail(@Valid @RequestBody ExistsEmailDTO existsEmailDTO){

        long startTime = System.nanoTime();
        Boolean check = this.userService.existsByEmail(existsEmailDTO);
        long elapsedTime = System.nanoTime() - startTime;
        log.info(format("%s: %.10f [s]", "register", (elapsedTime/Math.pow(10,9))));
        return new ResponseEntity(check, HttpStatus.OK);
    }

    @GetMapping("/test/hello")
    public String hello(){
        return "hello man!";
    }
}