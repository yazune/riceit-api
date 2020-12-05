package com.agh.riceitapi.controller;


import com.agh.riceitapi.dto.*;
import com.agh.riceitapi.model.Activity;
import com.agh.riceitapi.security.CurrentUser;
import com.agh.riceitapi.security.UserPrincipal;
import com.agh.riceitapi.service.ActivityService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.lang.String.format;

@RestController
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    private final Log log = LogFactory.getLog(getClass());

    @PostMapping("/activities/add")
    public ResponseEntity<Activity> addActivity(@CurrentUser UserPrincipal currentUser, @RequestBody AddActivityDTO addActivityDTO){
        long startTime = System.nanoTime();

        Activity activity = this.activityService.addActivity(currentUser.getId(), addActivityDTO);

        long elapsedTime = System.nanoTime() - startTime;
        log.info(format("%s in: %.10f [s]", "adding new activity", (elapsedTime/Math.pow(10,9))));

        return new ResponseEntity(activity, HttpStatus.OK);
    }

    @PostMapping("/activities/remove")
    public ResponseEntity<String> removeActivity(@CurrentUser UserPrincipal currentUser, @RequestBody RemoveActivityDTO removeActivityDTO){
        long startTime = System.nanoTime();

        this.activityService.removeActivity(currentUser.getId(), removeActivityDTO);

        long elapsedTime = System.nanoTime() - startTime;
        log.info(format("%s in: %.10f [s]", "removing an activity", (elapsedTime/Math.pow(10,9))));

        return new ResponseEntity("Activity: [" + removeActivityDTO.getActivityId() + "] successfully removed", HttpStatus.OK);
    }

    @PostMapping("/activities/update")
    public ResponseEntity<Activity> updateActivity(@CurrentUser UserPrincipal currentUser, @RequestBody UpdateActivityDTO updateActivityDTO){
        long startTime = System.nanoTime();

        Activity activity = this.activityService.updateActivity(currentUser.getId(), updateActivityDTO);

        long elapsedTime = System.nanoTime() - startTime;
        log.info(format("%s in: %.10f [s]", "updating an activity", (elapsedTime/Math.pow(10,9))));

        return new ResponseEntity(activity, HttpStatus.OK);
    }

    @PostMapping("/activities/showAll")
    public ResponseEntity<List<Activity>> showAllActivities(@CurrentUser UserPrincipal currentUser, @RequestBody DateDTO dateDTO){
        long startTime = System.nanoTime();

        List<Activity> activities = this.activityService.showAllActivities(currentUser.getId(), dateDTO);

        long elapsedTime = System.nanoTime() - startTime;
        log.info(format("%s in: %.10f [s]", "showing all activities", (elapsedTime/Math.pow(10,9))));

        return new ResponseEntity(activities, HttpStatus.OK);
    }

    @PostMapping("/activities/get")
    public ResponseEntity<Activity> getActivity(@CurrentUser UserPrincipal currentUser, @RequestBody GetActivityDTO getActivityDTO){
        long startTime = System.nanoTime();

        Activity activity = this.activityService.getActivity(currentUser.getId(), getActivityDTO);

        long elapsedTime = System.nanoTime() - startTime;
        log.info(format("%s in: %.10f [s]", "getting an activity", (elapsedTime/Math.pow(10,9))));

        return new ResponseEntity(activity, HttpStatus.OK);
    }
}
