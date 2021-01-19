package com.agh.riceitapi.service;

import com.agh.riceitapi.dto.GetGoalDTO;
import com.agh.riceitapi.dto.UpdateGoalDTO;
import com.agh.riceitapi.exception.UserNotFoundException;
import com.agh.riceitapi.model.DietType;
import com.agh.riceitapi.model.Goal;
import com.agh.riceitapi.model.User;
import com.agh.riceitapi.model.UserDetails;
import com.agh.riceitapi.repository.GoalRepository;
import com.agh.riceitapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GoalService {

    @Autowired
    private GoalRepository goalRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DayService dayService;

    public void autoCalculateParameters(long userId, UserDetails userDetails) throws UserNotFoundException{
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("There is no user with id: [" + userId + "]."));

        Goal goal = user.getGoal();
        goal.calculateParameters(userDetails);
        goalRepository.save(goal);

        dayService.updateTodaysDay(userId, goal);
    }

    public void updateManualParameters(long userId, UpdateGoalDTO updateGoalDTO) throws UserNotFoundException{
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("There is no user with id: [" + userId + "]."));

        Goal goal = user.getGoal();
        goal.updateManualParameters(updateGoalDTO);
        goalRepository.save(goal);
    }

    public GetGoalDTO getGoal(long userId) throws UserNotFoundException{
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("There is no user with id: [" + userId + "]."));

        GetGoalDTO getGoalDTO = new GetGoalDTO();
        getGoalDTO.fillDataWith(user.getGoal());
        return getGoalDTO;
    }

    public boolean areManParamsInUse(long userId){
        return goalRepository.areManParamsInUse(userId);
    }

    public void chooseManualOptions(long userId, boolean option){
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("There is no user with id: [" + userId + "]."));

        Goal goal = user.getGoal();
        goal.setManParamsInUse(option);
        goalRepository.save(goal);

        autoCalculateParameters(userId, user.getUserDetails());
    }

    public void chooseDietType(long userId, String dietType){
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("There is no user with id: [" + userId + "]."));

        Goal goal = user.getGoal();
        goal.setDietType(DietType.valueOf(dietType));
        goalRepository.save(goal);

        autoCalculateParameters(userId, user.getUserDetails());
    }

}
