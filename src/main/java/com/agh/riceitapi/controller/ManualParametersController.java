package com.agh.riceitapi.controller;

import com.agh.riceitapi.dto.ManualParametersDTO;
import com.agh.riceitapi.security.CurrentUser;
import com.agh.riceitapi.security.UserPrincipal;
import com.agh.riceitapi.service.ManualParametersService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static java.lang.String.format;

@Controller
public class ManualParametersController {

    @Autowired
    private ManualParametersService manualParametersService;

    private final Log log = LogFactory.getLog(getClass());


    @GetMapping("/manual")
    private ResponseEntity<ManualParametersDTO> getManualParameters(@CurrentUser UserPrincipal currentUser){
        long startTime = System.nanoTime();

        ManualParametersDTO dto = manualParametersService.getManualParameters(currentUser.getId());

        long elapsedTime = System.nanoTime() - startTime;
        log.info(format("%s in: %.10f [s]", "getting manual parameters", (elapsedTime/Math.pow(10,9))));

        return new ResponseEntity(dto, HttpStatus.OK);
    }

    @PostMapping("/manual")
    private ResponseEntity<String> updateManualParameters(@CurrentUser UserPrincipal currentUser, @RequestBody ManualParametersDTO dto){
        long startTime = System.nanoTime();

        manualParametersService.updateManualParameters(currentUser.getId(), dto);

        long elapsedTime = System.nanoTime() - startTime;
        log.info(format("%s in: %.10f [s]", "updating manual parameters", (elapsedTime/Math.pow(10,9))));

        return new ResponseEntity("Manual parameters successfully updated", HttpStatus.OK);
    }

}
