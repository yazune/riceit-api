package com.agh.riceitapi.controller;

import com.agh.riceitapi.dto.DateDTO;
import com.agh.riceitapi.dto.AddFoodDTO;
import com.agh.riceitapi.dto.GetFoodDTO;
import com.agh.riceitapi.dto.RemoveFoodDTO;
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

import java.util.List;

import static java.lang.String.format;

@RestController
public class MealController {

    @Autowired
    private MealService mealService;

    private final Log log = LogFactory.getLog(getClass());

    @PostMapping("/meals/create")
    public ResponseEntity<Meal> createMeal(@CurrentUser UserPrincipal currentUser, @RequestBody DateDTO dateDTO){
        long startTime = System.nanoTime();

        Meal meal = mealService.createMeal(currentUser.getId(), dateDTO);

        long elapsedTime = System.nanoTime() - startTime;
        log.info(format("%s in: %.10f [s]", "creating meal", (elapsedTime/Math.pow(10,9))));

        return new ResponseEntity(meal, HttpStatus.OK);
    }


//    @PostMapping("/meals/showAll")
//    public ResponseEntity<List<Meal>> showAll(@CurrentUser UserPrincipal currentUser, @RequestBody DateDTO dateDTO){
//        long startTime = System.nanoTime();
//
//        List<Meal> meals = mealService.showAll(currentUser.getId(), dateDTO);
//
//        long elapsedTime = System.nanoTime() - startTime;
//        log.info(format("%s in: %.10f [s]", "creating meal", (elapsedTime/Math.pow(10,9))));
//
//        return new ResponseEntity(meals, HttpStatus.OK);
//    }

    @PostMapping("/meals/addFood")
    public ResponseEntity addFood(@CurrentUser UserPrincipal currentUser, @RequestBody AddFoodDTO addFoodDTO){
        long startTime = System.nanoTime();

        Meal meal = mealService.addFood(currentUser.getId(), addFoodDTO);

        long elapsedTime = System.nanoTime() - startTime;
        log.info(format("%s in: %.10f [s]", "adding food", (elapsedTime/Math.pow(10,9))));

        return new ResponseEntity(meal, HttpStatus.OK);
    }

    @PostMapping("/meals/removeFood")
    public ResponseEntity removeFood(@CurrentUser UserPrincipal currentUser, @RequestBody RemoveFoodDTO removeFoodDTO){
        long startTime = System.nanoTime();

        Meal meal = mealService.removeFood(currentUser.getId(), removeFoodDTO);

        long elapsedTime = System.nanoTime() - startTime;
        log.info(format("%s in: %.10f [s]", "removing food", (elapsedTime/Math.pow(10,9))));

        return new ResponseEntity(meal, HttpStatus.OK);
    }

    @PostMapping("/meals/getFood")
    public ResponseEntity getFood(@CurrentUser UserPrincipal currentUser, @RequestBody GetFoodDTO getFoodDTO){
        long startTime = System.nanoTime();

        Food food = mealService.getFood(currentUser.getId(), getFoodDTO);

        long elapsedTime = System.nanoTime() - startTime;
        log.info(format("%s in: %.10f [s]", "removing food", (elapsedTime/Math.pow(10,9))));

        return new ResponseEntity(food, HttpStatus.OK);
    }


}
