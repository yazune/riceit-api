package com.agh.riceitapi.controller;

import com.agh.riceitapi.dto.*;
import com.agh.riceitapi.model.Food;
import com.agh.riceitapi.model.Meal;
import com.agh.riceitapi.security.CurrentUser;
import com.agh.riceitapi.security.UserPrincipal;
import com.agh.riceitapi.service.MealService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

import static java.lang.String.format;

@RestController
public class MealController {

    @Autowired
    private MealService mealService;

    private final Log log = LogFactory.getLog(getClass());

    @PostMapping("/meals/create")
    public ResponseEntity<String> createMeal(@CurrentUser UserPrincipal currentUser, @Valid @RequestBody DateDTO dateDTO){
        long startTime = System.nanoTime();

        mealService.createMeal(currentUser.getId(), dateDTO);

        long elapsedTime = System.nanoTime() - startTime;
        log.info(format("%s in: %.10f [s]", "creating meal", (elapsedTime/Math.pow(10,9))));

        return new ResponseEntity("Meal created succesfully.", HttpStatus.OK);
    }

    @PostMapping("/meals/showAll")
    public ResponseEntity<List<Meal>> showAllMeals(@CurrentUser UserPrincipal currentUser, @RequestBody DateDTO dateDTO){
        long startTime = System.nanoTime();

        AllMealsDTO allMealsDTO = mealService.showAllMeals(currentUser.getId(), dateDTO);

        long elapsedTime = System.nanoTime() - startTime;
        log.info(format("%s in: %.10f [s]", "showing all meals", (elapsedTime/Math.pow(10,9))));

        return new ResponseEntity(allMealsDTO, HttpStatus.OK);
    }

    @PostMapping("/meals/remove")
    public ResponseEntity<String> removeMeal(@CurrentUser UserPrincipal currentUser, @RequestBody RemoveMealDTO removeMealDTO){
        long startTime = System.nanoTime();

        mealService.removeMeal(currentUser.getId(), removeMealDTO);

        long elapsedTime = System.nanoTime() - startTime;
        log.info(format("%s in: %.10f [s]", "removing meal", (elapsedTime/Math.pow(10,9))));

        return new ResponseEntity("Meal removed successfully.", HttpStatus.OK);
    }

    @PostMapping("/meals/get")
    public ResponseEntity<Meal> getMeal(@CurrentUser UserPrincipal currentUser, @RequestBody GetMealDTO getMealDTO){
        long startTime = System.nanoTime();

        Meal meal = mealService.getMeal(currentUser.getId(), getMealDTO);

        long elapsedTime = System.nanoTime() - startTime;
        log.info(format("%s in: %.10f [s]", "getting meal", (elapsedTime/Math.pow(10,9))));

        return new ResponseEntity(meal, HttpStatus.OK);
    }

    @PostMapping("/meals/addFood")
    public ResponseEntity<Meal> addFood(@CurrentUser UserPrincipal currentUser, @RequestBody AddFoodDTO addFoodDTO){
        long startTime = System.nanoTime();

        mealService.addFood(currentUser.getId(), addFoodDTO);

        long elapsedTime = System.nanoTime() - startTime;
        log.info(format("%s in: %.10f [s]", "adding food", (elapsedTime/Math.pow(10,9))));

        return new ResponseEntity("Food added successfully.", HttpStatus.OK);
    }

    @PostMapping("/meals/removeFood")
    public ResponseEntity<Meal> removeFood(@CurrentUser UserPrincipal currentUser, @RequestBody RemoveFoodDTO removeFoodDTO){
        long startTime = System.nanoTime();

        mealService.removeFood(currentUser.getId(), removeFoodDTO);

        long elapsedTime = System.nanoTime() - startTime;
        log.info(format("%s in: %.10f [s]", "removing food", (elapsedTime/Math.pow(10,9))));

        return new ResponseEntity("Food removed successfully.", HttpStatus.OK);
    }

    @PostMapping("/meals/getFood")
    public ResponseEntity<Food> getFood(@CurrentUser UserPrincipal currentUser, @RequestBody GetFoodDTO getFoodDTO) {
        long startTime = System.nanoTime();

        Food food = mealService.getFood(currentUser.getId(), getFoodDTO);

        long elapsedTime = System.nanoTime() - startTime;
        log.info(format("%s in: %.10f [s]", "getting food", (elapsedTime/Math.pow(10,9))));

        return new ResponseEntity(food, HttpStatus.OK);
    }

    @PostMapping("/meals/updateFood")
    public ResponseEntity<Meal> updateFood(@CurrentUser UserPrincipal currentUser, @RequestBody UpdateFoodDTO editFoodDTO) throws IOException {
        long startTime = System.nanoTime();

        mealService.updateFood(currentUser.getId(), editFoodDTO);

        long elapsedTime = System.nanoTime() - startTime;
        log.info(format("%s in: %.10f [s]", "updating food", (elapsedTime/Math.pow(10,9))));

        return new ResponseEntity("Food updated successfully.", HttpStatus.OK);
    }
}
