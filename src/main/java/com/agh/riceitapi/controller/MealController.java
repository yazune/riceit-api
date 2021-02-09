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
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

import static java.lang.String.format;

@RestController
public class MealController {

    @Autowired
    private MealService mealService;

    private final Log log = LogFactory.getLog(getClass());

    @PostMapping("/meals")
    public ResponseEntity<String> createMeal(@CurrentUser UserPrincipal currentUser, @Valid @RequestBody DateDTO dateDTO){
        long startTime = System.nanoTime();

        mealService.createMeal(currentUser.getId(), dateDTO);

        long elapsedTime = System.nanoTime() - startTime;
        log.info(format("%s in: %.10f [s]", "creating meal", (elapsedTime/Math.pow(10,9))));

        return new ResponseEntity("Meal created succesfully.", HttpStatus.OK);
    }

    @PostMapping("/meals/all")
    public ResponseEntity<List<Meal>> getMeals(@CurrentUser UserPrincipal currentUser, @RequestBody DateDTO dateDTO){
        long startTime = System.nanoTime();

        MealsDTO mealsDTO = mealService.getMeals(currentUser.getId(), dateDTO);

        long elapsedTime = System.nanoTime() - startTime;
        log.info(format("%s in: %.10f [s]", "getting all meals", (elapsedTime/Math.pow(10,9))));

        return new ResponseEntity(mealsDTO, HttpStatus.OK);
    }

    @DeleteMapping("/meals/{mealId}")
    public ResponseEntity<String> removeMeal(@CurrentUser UserPrincipal currentUser, @PathVariable Long mealId){
        long startTime = System.nanoTime();

        mealService.removeMeal(currentUser.getId(), mealId);

        long elapsedTime = System.nanoTime() - startTime;
        log.info(format("%s in: %.10f [s]", "removing meal", (elapsedTime/Math.pow(10,9))));

        return new ResponseEntity("Meal removed successfully.", HttpStatus.OK);
    }

    @GetMapping("/meals/{mealId}")
    public ResponseEntity<Meal> getMeal(@CurrentUser UserPrincipal currentUser, @PathVariable Long mealId){
        long startTime = System.nanoTime();

        Meal meal = mealService.getMeal(currentUser.getId(), mealId);

        long elapsedTime = System.nanoTime() - startTime;
        log.info(format("%s in: %.10f [s]", "getting meal", (elapsedTime/Math.pow(10,9))));

        return new ResponseEntity(meal, HttpStatus.OK);
    }

    @PostMapping("/foods")
    public ResponseEntity<Meal> addFood(@CurrentUser UserPrincipal currentUser, @RequestBody FoodAddDTO foodAddDTO){
        long startTime = System.nanoTime();

        mealService.addFood(currentUser.getId(), foodAddDTO);

        long elapsedTime = System.nanoTime() - startTime;
        log.info(format("%s in: %.10f [s]", "adding food", (elapsedTime/Math.pow(10,9))));

        return new ResponseEntity("Food added successfully.", HttpStatus.OK);
    }

    @DeleteMapping("/foods/{foodId}")
    public ResponseEntity<Meal> removeFood(@CurrentUser UserPrincipal currentUser, @PathVariable Long foodId){
        long startTime = System.nanoTime();

        mealService.removeFood(currentUser.getId(), foodId);

        long elapsedTime = System.nanoTime() - startTime;
        log.info(format("%s in: %.10f [s]", "removing food", (elapsedTime/Math.pow(10,9))));

        return new ResponseEntity("Food removed successfully.", HttpStatus.OK);
    }

    @GetMapping("/foods/{foodId}")
    public ResponseEntity<Food> getFood(@CurrentUser UserPrincipal currentUser, @PathVariable Long foodId) {
        long startTime = System.nanoTime();

        Food food = mealService.getFood(currentUser.getId(), foodId);

        long elapsedTime = System.nanoTime() - startTime;
        log.info(format("%s in: %.10f [s]", "getting food", (elapsedTime/Math.pow(10,9))));

        return new ResponseEntity(food, HttpStatus.OK);
    }

    @PutMapping("/foods/{foodId}")
    public ResponseEntity<Meal> updateFood(@CurrentUser UserPrincipal currentUser, @PathVariable Long foodId, @RequestBody FoodDTO foodDTO) throws IOException {
        long startTime = System.nanoTime();

        mealService.updateFood(currentUser.getId(), foodId, foodDTO);

        long elapsedTime = System.nanoTime() - startTime;
        log.info(format("%s in: %.10f [s]", "updating food", (elapsedTime/Math.pow(10,9))));

        return new ResponseEntity("Food updated successfully.", HttpStatus.OK);
    }
}
