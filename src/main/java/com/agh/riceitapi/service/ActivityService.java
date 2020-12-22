package com.agh.riceitapi.service;

import com.agh.riceitapi.dto.*;
import com.agh.riceitapi.exception.ActivityNotFoundException;
import com.agh.riceitapi.exception.PermissionDeniedException;
import com.agh.riceitapi.exception.UserNotFoundException;
import com.agh.riceitapi.model.Activity;
import com.agh.riceitapi.model.Food;
import com.agh.riceitapi.model.User;
import com.agh.riceitapi.repository.ActivityRepository;
import com.agh.riceitapi.repository.UserRepository;
import com.agh.riceitapi.validator.DateValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Service
public class ActivityService {

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DayService dayService;

    public void addActivity(long userId, AddActivityDTO addActivityDTO) throws UserNotFoundException{

        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("There is no user with id: [" + userId + "]."));

        Activity activity = new Activity();

        LocalDate date = DateValidator.parseStrToLocalDate(addActivityDTO.getDate());
        activity.setDate(date);
        activity.setKcalBurnt(addActivityDTO.getKcalBurnt());
        activity.setName(addActivityDTO.getName());

        activity.createConnectionWithUser(user);
        activityRepository.save(activity);

        dayService.addActivity(userId, date, activity);
    }

    public void updateActivity(long userId, UpdateActivityDTO updateActivityDTO) throws ActivityNotFoundException, PermissionDeniedException, IOException {

        Activity activity = activityRepository.findById(updateActivityDTO.getActivityId()).orElseThrow(
                () -> new ActivityNotFoundException("There is no activity with id: [" + updateActivityDTO.getActivityId() + "]."));

        if (activity.getUser().getId() != userId){
            throw new PermissionDeniedException("Permission denied.");
        }

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        Activity activityBeforeChanges = objectMapper.readValue(objectMapper.writeValueAsString(activity), Activity.class);

        activity.setName(updateActivityDTO.getName());
        activity.setKcalBurnt(updateActivityDTO.getKcalBurnt());

        activityRepository.save(activity);

        dayService.updateActivity(userId, activity.getDate(), activityBeforeChanges, activity);
    }

    public void removeActivity(long userId, RemoveActivityDTO removeActivityDTO) throws ActivityNotFoundException, PermissionDeniedException{
        Activity activity = activityRepository.findById(removeActivityDTO.getActivityId()).orElseThrow(
                () -> new ActivityNotFoundException("There is no activity with id: [" + removeActivityDTO.getActivityId() + "]."));

        if (activity.getUser().getId() != userId){
            throw new PermissionDeniedException("Permission denied.");
        }

        activity.removeConnectionWithUser();
        activityRepository.delete(activity);

        dayService.removeActivity(userId, activity.getDate(), activity);
    }

    public Activity getActivity(long userId, GetActivityDTO getActivityDTO) throws ActivityNotFoundException, PermissionDeniedException{
        Activity activity = activityRepository.findById(getActivityDTO.getActivityId()).orElseThrow(
                () -> new ActivityNotFoundException("There is no activity with id: [" + getActivityDTO.getActivityId() + "]."));

        if (activity.getUser().getId() != userId){
            throw new PermissionDeniedException("Permission denied.");
        }

        return activity;
    }

    public List<Activity> showAllActivities(long userId, DateDTO dateDTO){
        LocalDate date = DateValidator.parseStrToLocalDate(dateDTO.getDate());
        return activityRepository.findAllByUserIdAndDate(userId, date);
    }

}
