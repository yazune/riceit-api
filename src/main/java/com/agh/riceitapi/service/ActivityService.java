package com.agh.riceitapi.service;

import com.agh.riceitapi.dto.*;
import com.agh.riceitapi.exception.ActivityServiceException;
import com.agh.riceitapi.model.Activity;
import com.agh.riceitapi.model.User;
import com.agh.riceitapi.repository.ActivityRepository;
import com.agh.riceitapi.repository.UserRepository;
import com.agh.riceitapi.validator.DateValidator;
import com.agh.riceitapi.validator.DateValidatorUsingDateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ActivityService {

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private UserRepository userRepository;

    public Activity addActivity(long userId, AddActivityDTO addActivityDTO){

        User user = this.userRepository.findById(userId).orElseThrow(
                () -> new ActivityServiceException("There is no user with id: [" + userId + "]."));

        Activity activity = new Activity();

        LocalDate date = parseStrToLocalDate(addActivityDTO.getDate());
        activity.setDate(date);
        activity.setKcalBurnt(addActivityDTO.getKcalBurnt());
        activity.setName(addActivityDTO.getName());

        activity.addUser(user);
        return this.activityRepository.save(activity);
    }

    public Activity updateActivity(long userId, UpdateActivityDTO updateActivityDTO){

        Activity activity = this.activityRepository.findByIdAndUserId(updateActivityDTO.getActivityId(), userId).orElseThrow(
                () -> new ActivityServiceException("There is no activity with id: [" + updateActivityDTO.getActivityId()
                        + "] for user: [" + userId + "]."));

        activity.setName(updateActivityDTO.getName());
        activity.setKcalBurnt(updateActivityDTO.getKcalBurnt());

        return this.activityRepository.save(activity);
    }

    public void removeActivity(long userId, RemoveActivityDTO removeActivityDTO){
        Activity activity = this.activityRepository.findByIdAndUserId(removeActivityDTO.getActivityId(), userId).orElseThrow(
                () -> new ActivityServiceException("There is no activity with id: [" + removeActivityDTO.getActivityId()
                        + "] for user: [" + userId + "]."));

        activity.removeUser();
        this.activityRepository.delete(activity);
    }

    public Activity getActivity(long userId, GetActivityDTO getActivityDTO){
        Activity activity = this.activityRepository.findByIdAndUserId(getActivityDTO.getActivityId(), userId).orElseThrow(
                () -> new ActivityServiceException("There is no activity with id: [" + getActivityDTO.getActivityId()
                        + "] for user: [" + userId + "]."));

        return activity;
    }

    public List<Activity> showAllActivities(long userId, DateDTO dateDTO){
        LocalDate date = parseStrToLocalDate(dateDTO.getDate());
        return this.activityRepository.findAllByUserIdAndDate(userId, date);
    }

    public LocalDate parseStrToLocalDate(String dateStr){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateValidator dateValidator = new DateValidatorUsingDateTimeFormatter(dtf);
        if (dateValidator.isValid(dateStr)) {
            return LocalDate.parse(dateStr);
        } else throw new ActivityServiceException("Wrong data format (should be yyyy-MM-dd)");
    }

}
