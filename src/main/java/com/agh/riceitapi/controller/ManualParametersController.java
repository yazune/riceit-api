package com.agh.riceitapi.controller;

import com.agh.riceitapi.dto.ManualParametersDTO;
import com.agh.riceitapi.log.LogExecutionTime;
import com.agh.riceitapi.security.CurrentUser;
import com.agh.riceitapi.security.UserPrincipal;
import com.agh.riceitapi.service.ManualParametersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class ManualParametersController {

    @Autowired
    private ManualParametersService manualParametersService;

    @GetMapping("/manual")
    @LogExecutionTime
    private ResponseEntity<ManualParametersDTO> getManualParameters(@CurrentUser UserPrincipal currentUser){
        ManualParametersDTO dto = manualParametersService.getManualParameters(currentUser.getId());
        return new ResponseEntity(dto, HttpStatus.OK);
    }

    @PutMapping("/manual")
    @LogExecutionTime
    private ResponseEntity<String> updateManualParameters(@CurrentUser UserPrincipal currentUser, @RequestBody ManualParametersDTO manualParametersDTO){
        manualParametersService.updateManualParameters(currentUser.getId(), manualParametersDTO);
        return new ResponseEntity("Manual parameters successfully updated", HttpStatus.OK);
    }

}
