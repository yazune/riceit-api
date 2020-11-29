package com.agh.riceitapi.service;

import com.agh.riceitapi.dto.DateDTO;
import com.agh.riceitapi.dto.AddFoodDTO;
import com.agh.riceitapi.dto.GetFoodDTO;
import com.agh.riceitapi.dto.RemoveFoodDTO;
import com.agh.riceitapi.exception.FoodNotFoundException;
import com.agh.riceitapi.exception.MealNotFoundException;
import com.agh.riceitapi.exception.UserNotFoundException;
import com.agh.riceitapi.model.Food;
import com.agh.riceitapi.model.Meal;
import com.agh.riceitapi.model.User;
import com.agh.riceitapi.repository.FoodRepository;
import com.agh.riceitapi.repository.MealRepository;
import com.agh.riceitapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class MealService {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MealRepository mealRepository;

    @Autowired
    private FoodRepository foodRepository;

    public Meal createMeal(long userId, DateDTO dateDTO){

        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("There is no user with id: ["+userId+"]."));

        Meal meal = new Meal();
        meal.setUser(user);
        meal.setDate(dateDTO.getDate());

        return this.mealRepository.save(meal);
    }

//    public List<Meal> showAll(long userId, DateDTO dateDTO){
//        //TODO - write a query
//    }

    public Meal addFood(long userId, AddFoodDTO foodDTO){

        User user = this.userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("There is no user with id: [" + userId + "]."));

        Meal meal = this.mealRepository.findById(foodDTO.getMealId()).orElseThrow(
                () -> new MealNotFoundException("There is no meal with id: [" + foodDTO.getMealId() + "]."));

        Food food = new Food();
        food.setName(foodDTO.getName());
        food.setKcal(foodDTO.getKcal());
        food.setCarbohydrate(foodDTO.getCarbohydrate());
        food.setFat(foodDTO.getFat());
        food.setProtein(foodDTO.getProtein());

        meal.getFoods().add(food);

        this.foodRepository.save(food);
        return this.mealRepository.save(meal);
    }

    public Meal removeFood(long userId, RemoveFoodDTO removeFoodDTO){

        User user = this.userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("There is no user with id: [" + userId + "]."));

        Meal meal = this.mealRepository.findById(removeFoodDTO.getMealId()).orElseThrow(
                () -> new MealNotFoundException("There is no meal with id: [" + removeFoodDTO.getMealId() + "]."));

        Food food = this.foodRepository.findById(removeFoodDTO.getFoodId()).orElseThrow(
                () -> new FoodNotFoundException("There is no food with id: ]" + removeFoodDTO.getFoodId() + "]."));

        meal.getFoods().remove(food);
        this.foodRepository.delete(food);
        return this.mealRepository.save(meal);
    }

    public Food getFood(long userId, GetFoodDTO getFoodDTO){
        return this.foodRepository.findById(getFoodDTO.getFoodId()).orElseThrow(
                () -> new FoodNotFoundException("There is no food with id: ]" + getFoodDTO.getFoodId() + "]."));
    }



}
