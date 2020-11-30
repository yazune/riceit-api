package com.agh.riceitapi.controller;

import com.agh.riceitapi.dto.*;
import com.agh.riceitapi.exception.MealServiceException;
import com.agh.riceitapi.exception.RegisterException;
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
import java.util.List;

import static java.lang.String.format;

@RestController
public class MealController {

    @Autowired
    private MealService mealService;

    private final Log log = LogFactory.getLog(getClass());

    @PostMapping("/meals/create")
    public ResponseEntity<Meal> createMeal(@CurrentUser UserPrincipal currentUser, @Valid @RequestBody DateDTO dateDTO) throws MealServiceException {
        long startTime = System.nanoTime();

        Meal meal = this.mealService.createMeal(currentUser.getId(), dateDTO);

        long elapsedTime = System.nanoTime() - startTime;
        log.info(format("%s in: %.10f [s]", "creating meal", (elapsedTime/Math.pow(10,9))));

        return new ResponseEntity(meal, HttpStatus.OK);
    }

    @PostMapping("/meals/showAll")
    public ResponseEntity<List<Meal>> showAll(@CurrentUser UserPrincipal currentUser, @RequestBody DateDTO dateDTO) throws MealServiceException{
        long startTime = System.nanoTime();

        List<Meal> meals = this.mealService.showAll(currentUser.getId(), dateDTO);

        long elapsedTime = System.nanoTime() - startTime;
        log.info(format("%s in: %.10f [s]", "showing all meals", (elapsedTime/Math.pow(10,9))));

        return new ResponseEntity(meals, HttpStatus.OK);
    }

    @PostMapping("/meals/remove")
    public ResponseEntity RemoveMeal(@CurrentUser UserPrincipal currentUser, @RequestBody RemoveMealDTO removeMealDTO) throws MealServiceException{
        long startTime = System.nanoTime();

        this.mealService.removeMeal(currentUser.getId(), removeMealDTO);

        long elapsedTime = System.nanoTime() - startTime;
        log.info(format("%s in: %.10f [s]", "removing meal", (elapsedTime/Math.pow(10,9))));

        return new ResponseEntity("Meal: [" + removeMealDTO.getMealId() + "] successfully removed", HttpStatus.OK);
    }

    @PostMapping("/meals/addFood")
    public ResponseEntity addFood(@CurrentUser UserPrincipal currentUser, @RequestBody AddFoodDTO addFoodDTO) throws MealServiceException{
        long startTime = System.nanoTime();

        Meal meal = this.mealService.addFood(currentUser.getId(), addFoodDTO);

        long elapsedTime = System.nanoTime() - startTime;
        log.info(format("%s in: %.10f [s]", "adding food", (elapsedTime/Math.pow(10,9))));

        return new ResponseEntity(meal, HttpStatus.OK);
    }

    @PostMapping("/meals/removeFood")
    public ResponseEntity removeFood(@CurrentUser UserPrincipal currentUser, @RequestBody RemoveFoodDTO removeFoodDTO) throws MealServiceException{
        long startTime = System.nanoTime();

        Meal meal = this.mealService.removeFood(currentUser.getId(), removeFoodDTO);

        long elapsedTime = System.nanoTime() - startTime;
        log.info(format("%s in: %.10f [s]", "removing food", (elapsedTime/Math.pow(10,9))));

        return new ResponseEntity(meal, HttpStatus.OK);
    }

    @PostMapping("/meals/getFood")
    public ResponseEntity getFood(@CurrentUser UserPrincipal currentUser, @RequestBody GetFoodDTO getFoodDTO) throws MealServiceException{
        long startTime = System.nanoTime();

        Food food = this.mealService.getFood(currentUser.getId(), getFoodDTO);

        long elapsedTime = System.nanoTime() - startTime;
        log.info(format("%s in: %.10f [s]", "getting food", (elapsedTime/Math.pow(10,9))));

        return new ResponseEntity(food, HttpStatus.OK);
    }

    @PostMapping("/meals/updateFood")
    public ResponseEntity<Meal> updateFood(@CurrentUser UserPrincipal currentUser, @RequestBody UpdateFoodDTO editFoodDTO) throws MealServiceException{
        long startTime = System.nanoTime();

        Meal meal = this.mealService.updateFood(currentUser.getId(), editFoodDTO);

        long elapsedTime = System.nanoTime() - startTime;
        log.info(format("%s in: %.10f [s]", "getting food", (elapsedTime/Math.pow(10,9))));

        return new ResponseEntity(meal, HttpStatus.OK);
    }



}
