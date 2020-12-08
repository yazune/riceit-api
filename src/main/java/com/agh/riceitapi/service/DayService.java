package com.agh.riceitapi.service;

import com.agh.riceitapi.dto.DateDTO;
import com.agh.riceitapi.exception.DayNotFoundException;
import com.agh.riceitapi.exception.UserNotFoundException;
import com.agh.riceitapi.model.*;
import com.agh.riceitapi.repository.DayRepository;
import com.agh.riceitapi.repository.UserRepository;
import com.agh.riceitapi.validator.DateValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class DayService {

    @Autowired
    private DayRepository dayRepository;

    @Autowired
    private UserRepository userRepository;

    public Day getDay(long userId, DateDTO dateDTO){
        LocalDate date = DateValidator.parseStrToLocalDate(dateDTO.getDate());

        return dayRepository.findByUserIdAndDate(userId, date).orElseGet(
                () -> createDay(userId, date)
        );
    }

    public Day createDay(long userId, LocalDate date){
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("There is no user with id: [" + userId + "]."));

        Goal goal = user.getGoal();
        Day day = new Day();
        day.fillWithDataFrom(goal);
        day.setDate(date);
        day.createConnectionWithUser(user);
        return dayRepository.save(day);
    }

    public void addFood(long userId, LocalDate date, Food food){
        Day day = dayRepository.findByUserIdAndDate(userId, date).orElseThrow(
                () -> new DayNotFoundException("There is no day to update."));
        day.addMacroFromFood(food);
        dayRepository.save(day);
    }

    public void removeFood(long userId, LocalDate date, Food food){
        Day day = dayRepository.findByUserIdAndDate(userId, date).orElseThrow(
                () -> new DayNotFoundException("There is no day to update."));
        day.removeMacroFromFood(food);
        dayRepository.save(day);
    }

    public void removeMeal(long userId, LocalDate date, Meal meal){
        Day day = dayRepository.findByUserIdAndDate(userId, date).orElseThrow(
                () -> new DayNotFoundException("There is no day to update."));
        day.removeMacroFromMeal(meal);
        dayRepository.save(day);
    }

}
