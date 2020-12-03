package com.agh.riceitapi.controller;


import com.agh.riceitapi.repository.ActivityRepository;
import com.agh.riceitapi.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ActivityController {

    @Autowired
    private ActivityService activityService;

}
