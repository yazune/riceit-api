package com.agh.riceitapi.controller;

import com.agh.riceitapi.exception.AppException;
import com.agh.riceitapi.model.Role;
import com.agh.riceitapi.model.RoleName;
import com.agh.riceitapi.model.User;
import com.agh.riceitapi.repository.RoleRepository;
import com.agh.riceitapi.repository.UserRepository;
import com.agh.riceitapi.request.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Collections;

@RestController
public class AuthenticationController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping("/auth/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest registerRequest){
        if(userRepository.existsByUsername(registerRequest.getUsername())){
            return new ResponseEntity("Username already in use", HttpStatus.BAD_REQUEST);
        }
        if(userRepository.existsByEmail(registerRequest.getEmail())){
            return new ResponseEntity("Email already in use", HttpStatus.BAD_REQUEST);
        }

        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        Role role = roleRepository.findByName(RoleName.ROLE_USER).orElseThrow(
                () -> new AppException("User role not set!"));
        user.setRoles(Collections.singleton(role));
        userRepository.save(user);

        return new ResponseEntity("User registered succesfully!", HttpStatus.OK);
    }

    @GetMapping("/hello")
    public String hello(){
        return "hello!";
    }
}