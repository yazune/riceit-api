package com.agh.riceitapi.controller;

import com.agh.riceitapi.dto.*;
import com.agh.riceitapi.log.LogExecutionTime;
import com.agh.riceitapi.model.Food;
import com.agh.riceitapi.model.Meal;
import com.agh.riceitapi.security.CurrentUser;
import com.agh.riceitapi.security.UserPrincipal;
import com.agh.riceitapi.service.MealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
public class MealController {

    @Autowired
    private MealService mealService;

    @PostMapping("/meals")
    @LogExecutionTime
    public ResponseEntity<String> createMeal(@CurrentUser UserPrincipal currentUser, @Valid @RequestBody DateDTO dateDTO){
        mealService.createMeal(currentUser.getId(), dateDTO);
        return new ResponseEntity("Meal created succesfully.", HttpStatus.OK);
    }

    @PostMapping("/meals/all")
    @LogExecutionTime
    public ResponseEntity<List<Meal>> getMeals(@CurrentUser UserPrincipal currentUser, @RequestBody DateDTO dateDTO){
        MealsDTO mealsDTO = mealService.getMeals(currentUser.getId(), dateDTO);
        return new ResponseEntity(mealsDTO, HttpStatus.OK);
    }

    @DeleteMapping("/meals/{mealId}")
    @LogExecutionTime
    public ResponseEntity<String> removeMeal(@CurrentUser UserPrincipal currentUser, @PathVariable Long mealId){
        mealService.removeMeal(currentUser.getId(), mealId);
        return new ResponseEntity("Meal removed successfully.", HttpStatus.OK);
    }

    @GetMapping("/meals/{mealId}")
    @LogExecutionTime
    public ResponseEntity<Meal> getMeal(@CurrentUser UserPrincipal currentUser, @PathVariable Long mealId){
        Meal meal = mealService.getMeal(currentUser.getId(), mealId);
        return new ResponseEntity(meal, HttpStatus.OK);
    }

    @PostMapping("/foods")
    @LogExecutionTime
    public ResponseEntity<String> addFood(@CurrentUser UserPrincipal currentUser, @RequestBody FoodAddDTO foodAddDTO){
        mealService.addFood(currentUser.getId(), foodAddDTO);
        return new ResponseEntity("Food added successfully.", HttpStatus.OK);
    }

    @DeleteMapping("/foods/{foodId}")
    @LogExecutionTime
    public ResponseEntity<String> removeFood(@CurrentUser UserPrincipal currentUser, @PathVariable Long foodId){
        mealService.removeFood(currentUser.getId(), foodId);
        return new ResponseEntity("Food removed successfully.", HttpStatus.OK);
    }

    @GetMapping("/foods/{foodId}")
    @LogExecutionTime
    public ResponseEntity<Food> getFood(@CurrentUser UserPrincipal currentUser, @PathVariable Long foodId) {
        Food food = mealService.getFood(currentUser.getId(), foodId);
        return new ResponseEntity(food, HttpStatus.OK);
    }

    @PutMapping("/foods/{foodId}")
    @LogExecutionTime
    public ResponseEntity<Meal> updateFood(@CurrentUser UserPrincipal currentUser, @PathVariable Long foodId, @RequestBody FoodDTO foodDTO) throws IOException {
        mealService.updateFood(currentUser.getId(), foodId, foodDTO);
        return new ResponseEntity("Food updated successfully.", HttpStatus.OK);
    }
}
