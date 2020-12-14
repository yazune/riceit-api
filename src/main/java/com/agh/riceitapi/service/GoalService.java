package com.agh.riceitapi.service;

import com.agh.riceitapi.dto.UpdateGoalDTO;
import com.agh.riceitapi.exception.UserNotFoundException;
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

    public void autoCalculateParameters(long userId, UserDetails userDetails) throws UserNotFoundException{
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("There is no user with id: [" + userId + "]."));

        Goal goal = user.getGoal();
        goal.calculateParameters(userDetails);
        goalRepository.save(goal);
    }

    public void updateManualParameters(long userId, UpdateGoalDTO updateGoalDTO) throws UserNotFoundException{
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("There is no user with id: [" + userId + "]."));

        Goal goal = user.getGoal();
        goal.updateManualParameters(updateGoalDTO);
        goalRepository.save(goal);
    }

    public Goal getGoal(long userId) throws UserNotFoundException{
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("There is no user with id: [" + userId + "]."));

        return user.getGoal();
    }

    public boolean areManParamsInUse(long userId){
        return goalRepository.areManParamsInUse(userId);
    }

}
