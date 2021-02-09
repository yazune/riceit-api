package com.agh.riceitapi.service;

import com.agh.riceitapi.dto.DateDTO;
import com.agh.riceitapi.exception.DayNotFoundException;
import com.agh.riceitapi.exception.UserNotFoundException;
import com.agh.riceitapi.model.*;
import com.agh.riceitapi.repository.DayRepository;
import com.agh.riceitapi.repository.UserRepository;
import com.agh.riceitapi.util.DietParamCalculator;
import com.agh.riceitapi.util.DietType;
import com.agh.riceitapi.util.DateValidator;
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
                () -> createDay(userId, date));
    }

    public Day getLastDay(long userId){
        return dayRepository.findTopByUserIdOrderByDateDesc(userId).orElseGet(
                () -> {throw new DayNotFoundException("There is no days for user with id: [" + userId + "].");});
    }

    public Day createDay(long userId, LocalDate date){
        User user = userRepository.findById(userId).orElseGet(
                () -> {throw new UserNotFoundException("There is no user with id: [" + userId + "].");});

        UserSettings settings = user.getUserSettings();
        Day day = new Day();

        if (settings.isUseMan()){
            ManualParameters mp = user.getManualParameters();
            day.setKcalToEat(mp.getManKcal());
            day.setProteinToEat(mp.getManProtein());
            day.setFatToEat(mp.getManFat());
            day.setCarbohydrateToEat(mp.getManCarbohydrate());
        } else {
            UserDetails details = user.getUserDetails();
            double pal;
            if (settings.isUsePal()){
                pal = details.getPal();
            } else pal = 1.0;

            double difference;
            if(settings.getDietType().equals(DietType.GAIN)){
                difference = 500.0;
            } else if(settings.getDietType().equals(DietType.REDUCTION)){
                difference = -500.0;
            } else difference = 0.0;

            double[] array = DietParamCalculator.calculateMacro(details.getBmr(), pal, difference);

            day.setKcalToEat(array[0]);
            day.setProteinToEat(array[1]);
            day.setFatToEat(array[2]);
            day.setCarbohydrateToEat(array[3]);
            day.setUsePal(settings.isUsePal());
        }
        day.setDate(date);
        day.createConnectionWithUser(user);
        return dayRepository.save(day);
    }

    public void updateLastDay(long userId){
        Day day = dayRepository.findTopByUserIdOrderByDateDesc(userId).orElseGet(
                () -> {throw new DayNotFoundException("There is no days for user with id: [" + userId + "].");});


        User user = day.getUser();
        UserSettings settings = user.getUserSettings();

        if (settings.isUseMan()){
            ManualParameters mp = user.getManualParameters();
            day.setKcalToEat(mp.getManKcal());
            day.setProteinToEat(mp.getManProtein());
            day.setFatToEat(mp.getManFat());
            day.setCarbohydrateToEat(mp.getManCarbohydrate());
        } else {
            UserDetails details = user.getUserDetails();
            double pal;
            if (settings.isUsePal()){
                pal = details.getPal();
            } else pal = 1.0;

            double difference;
            if(settings.getDietType().equals(DietType.GAIN)){
                difference = 500.0;
            } else if(settings.getDietType().equals(DietType.REDUCTION)){
                difference = -500.0;
            } else difference = 0.0;

            double[] array = DietParamCalculator.calculateMacro(details.getBmr(), pal, difference);

            day.setKcalToEat(array[0]);
            day.setProteinToEat(array[1]);
            day.setFatToEat(array[2]);
            day.setCarbohydrateToEat(array[3]);
            day.setUsePal(settings.isUsePal());
        }

        dayRepository.save(day);
    }

    public void addFood(long userId, LocalDate date, Food food){
        Day day = dayRepository.findByUserIdAndDate(userId, date).orElseGet(
                () -> createDay(userId, date));

        day.addMacroFromFood(food);
        dayRepository.save(day);
    }

    public void removeFood(long userId, LocalDate date, Food food){
        Day day = dayRepository.findByUserIdAndDate(userId, date).orElseGet(
                () -> createDay(userId, date));

        day.removeMacroFromFood(food);
        dayRepository.save(day);
    }

    public void removeMeal(long userId, LocalDate date, Meal meal){
        Day day = dayRepository.findByUserIdAndDate(userId, date).orElseGet(
                () -> createDay(userId, date));

        day.removeMacroFromMeal(meal);
        dayRepository.save(day);
    }

    public void updateFood(long userId, LocalDate date, Food foodBeforeChanges, Food foodAfterChanges){
        Day day = dayRepository.findByUserIdAndDate(userId, date).orElseGet(
                () -> createDay(userId, date));
        day.removeMacroFromFood(foodBeforeChanges);
        day.addMacroFromFood(foodAfterChanges);
        dayRepository.save(day);
    }

    public void addSport(long userId, LocalDate date, Sport sport){
        Day day = dayRepository.findByUserIdAndDate(userId, date).orElseGet(
                () -> createDay(userId, date));

        double kcalBurnt = sport.getKcalBurnt();

        double[] pfc = DietParamCalculator.calculatePFC(kcalBurnt);
        day.addMacroBurnt(kcalBurnt, pfc[0], pfc[1], pfc[2]);
        dayRepository.save(day);
    }

    public void removeSport(long userId, LocalDate date, Sport sport){
        Day day = dayRepository.findByUserIdAndDate(userId, date).orElseGet(
                () -> createDay(userId, date));

        double kcalBurnt = sport.getKcalBurnt();

        double[] pfc = DietParamCalculator.calculatePFC(kcalBurnt);
        day.subtractMacroBurnt(kcalBurnt, pfc[0], pfc[1], pfc[2]);

        dayRepository.save(day);
    }

    public void updateSport(long userId, LocalDate date, Sport sportBeforeChanges, Sport sportAfterChanges){
        Day day = dayRepository.findByUserIdAndDate(userId, date).orElseGet(
                () -> createDay(userId, date));


        double kcalBurntBefore = sportBeforeChanges.getKcalBurnt();
        double kcalBurntAfter = sportAfterChanges.getKcalBurnt();

        double[] pfcBefore = DietParamCalculator.calculatePFC(kcalBurntBefore);
        double[] pfcAfter = DietParamCalculator.calculatePFC(kcalBurntAfter);

        day.subtractMacroBurnt(kcalBurntBefore, pfcBefore[0], pfcBefore[1], pfcBefore[2]);
        day.addMacroBurnt(kcalBurntAfter, pfcAfter[0], pfcAfter[1], pfcAfter[2]);

        dayRepository.save(day);
    }

}
