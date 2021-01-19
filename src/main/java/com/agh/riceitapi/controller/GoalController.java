package com.agh.riceitapi.controller;


import com.agh.riceitapi.dto.GetGoalDTO;
import com.agh.riceitapi.dto.UpdateGoalDTO;
import com.agh.riceitapi.model.Goal;
import com.agh.riceitapi.security.CurrentUser;
import com.agh.riceitapi.security.UserPrincipal;
import com.agh.riceitapi.service.GoalService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static java.lang.String.format;

@RestController
public class GoalController {

    @Autowired
    private GoalService goalService;

    private final Log log = LogFactory.getLog(getClass());

    @PostMapping("/user/updateGoal")
    public ResponseEntity<String> updateManualParameters(@CurrentUser UserPrincipal currentUser, @RequestBody UpdateGoalDTO updateGoalDTO){
        long startTime = System.nanoTime();

        goalService.updateManualParameters(currentUser.getId(),updateGoalDTO);

        long elapsedTime = System.nanoTime() - startTime;
        log.info(format("%s in: %.10f [s]", "updating a goal", (elapsedTime/Math.pow(10,9))));

        return new ResponseEntity("Goal updated succesfully.", HttpStatus.OK);
    }

    @GetMapping("/user/getGoal")
    public ResponseEntity<GetGoalDTO> getGoal(@CurrentUser UserPrincipal currentUser){
        long startTime = System.nanoTime();

        GetGoalDTO getGoalDTO =  goalService.getGoal(currentUser.getId());

        long elapsedTime = System.nanoTime() - startTime;
        log.info(format("%s in: %.10f [s]", "getting a goal", (elapsedTime/Math.pow(10,9))));

        return new ResponseEntity(getGoalDTO, HttpStatus.OK);

    }

    @GetMapping("/user/checkGoal")
    public ResponseEntity<Boolean> areManParamsInUse(@CurrentUser UserPrincipal currentUser){
        long startTime = System.nanoTime();

        Boolean checker =  goalService.areManParamsInUse(currentUser.getId());

        long elapsedTime = System.nanoTime() - startTime;
        log.info(format("%s in: %.10f [s]", "checking if manual parameters are in use", (elapsedTime/Math.pow(10,9))));

        return new ResponseEntity(checker, HttpStatus.OK);
    }

}
