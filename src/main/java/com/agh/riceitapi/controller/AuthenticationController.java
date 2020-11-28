package com.agh.riceitapi.controller;

import com.agh.riceitapi.dto.LoginDTO;
import com.agh.riceitapi.dto.TokenDTO;
import com.agh.riceitapi.security.JwtTokenProvider;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static java.lang.String.format;

@RestController
public class AuthenticationController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    private final Log log = LogFactory.getLog(getClass());

    @PostMapping("/auth/login")
    public ResponseEntity<TokenDTO> login(@Valid @RequestBody LoginDTO loginDTO){

        long startTime = System.nanoTime();
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDTO.getUsernameOrEmail(),
                        loginDTO.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtTokenProvider.generateToken(authentication);

        long elapsedTime = System.nanoTime() - startTime;
        log.info(format("%s: %.10f [s]", "register", (elapsedTime/Math.pow(10,9))));
        return new ResponseEntity(new TokenDTO("Bearer", jwt), HttpStatus.OK);
    }
}
