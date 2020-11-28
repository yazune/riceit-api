package com.agh.riceitapi.controller;

import com.agh.riceitapi.dto.RegisterDTO;
import com.agh.riceitapi.exception.RegisterException;
import com.agh.riceitapi.model.User;
import com.agh.riceitapi.service.UserService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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
       if (userService.existsByUsername(registerDTO.getUsername())){
           throw new RegisterException("register.error.usernameExists");
       }
       if (userService.existsByEmail(registerDTO.getEmail())){
           throw new RegisterException("register.error.emailExists");
       }

       User user = this.userService.createUser(registerDTO);
       long elapsedTime = System.nanoTime() - startTime;
       log.info(format("%s: %.10f [s]", "register", (elapsedTime/Math.pow(10,9))));
       return new ResponseEntity(user, HttpStatus.OK);
    }




}