package com.agh.riceitapi.service;

import com.agh.riceitapi.dto.*;
import com.agh.riceitapi.exception.MealServiceException;
import com.agh.riceitapi.model.Food;
import com.agh.riceitapi.model.Meal;
import com.agh.riceitapi.model.User;
import com.agh.riceitapi.repository.FoodRepository;
import com.agh.riceitapi.repository.MealRepository;
import com.agh.riceitapi.repository.UserRepository;
import com.agh.riceitapi.validator.DateValidator;
import com.agh.riceitapi.validator.DateValidatorUsingDateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
        User user = this.userRepository.findById(userId).orElseThrow(
                () -> new MealServiceException("There is no user with id: [" + userId + "]."));

        Meal meal = new Meal();

        LocalDate date = parseStrToLocalDate(dateDTO.getDate());
        meal.setDate(date);
        meal.addUser(user);

        return this.mealRepository.save(meal);
    }

    public List<Meal> showAllMeals(long userId, DateDTO dateDTO){
        LocalDate date = parseStrToLocalDate(dateDTO.getDate());
        return this.mealRepository.findAllByUserIdAndDate(userId, date);
    }

    public void removeMeal(long userId, RemoveMealDTO removeMealDTO){
        Meal meal = this.mealRepository.findByIdAndUserId(removeMealDTO.getMealId(), userId).orElseThrow(
                () -> new MealServiceException("There is no meal with id: [" + removeMealDTO.getMealId() + "]."));

        meal.removeUser();
        this.mealRepository.delete(meal);
    }

    public Meal addFood(long userId, AddFoodDTO addFoodDTO){
        Meal meal = this.mealRepository.findByIdAndUserId(addFoodDTO.getMealId(), userId).orElseThrow(
                () -> new MealServiceException("There is no meal with id: [" + addFoodDTO.getMealId() + "]."));

        Food food = new Food();
        food.fillWithDataFrom(addFoodDTO);

        meal.addFood(food);
        return this.mealRepository.save(meal);
    }

    public Meal updateFood(long userId, UpdateFoodDTO updateFoodDTO){
        Food food = this.foodRepository.findById(updateFoodDTO.getFoodId()).orElseThrow(
                () -> new MealServiceException("There is no food with id: [" + updateFoodDTO.getFoodId() + "]."));

        Meal meal = food.getMeal();

        if (meal.getUser().getId() != userId){
            throw new MealServiceException("There is no food: [" + food.getId() + "] for user: [" + userId + "].");
        }

        meal.removeFood(food);

        food.fillWithDataFrom(updateFoodDTO);

        meal.addFood(food);

        return this.mealRepository.save(meal);
    }

    public Meal removeFood(long userId, RemoveFoodDTO removeFoodDTO){
        Food food = this.foodRepository.findById(removeFoodDTO.getFoodId()).orElseThrow(
                () -> new MealServiceException("There is no food with id: [" + removeFoodDTO.getFoodId() + "]."));
        Meal meal = food.getMeal();

        //todo - change the http_status for 401 unauthorized
        if (meal.getUser().getId() != userId){
            throw new MealServiceException ("There is no food: [" + food.getId() + "] for user: [" + userId + "].");
        }

        meal.removeFood(food);

        return this.mealRepository.save(meal);
    }

    public Food getFood(long userId, GetFoodDTO getFoodDTO){
        Food food = this.foodRepository.findById(getFoodDTO.getFoodId()).orElseThrow(
                () -> new MealServiceException("There is no food with id: ]" + getFoodDTO.getFoodId() + "]."));

        if (food.getMeal().getUser().getId() != userId){
            throw new MealServiceException("There is no food: [" + food.getId() + "] for user: [" + userId + "].");
        } else return food;
    }

    public LocalDate parseStrToLocalDate(String dateStr){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateValidator dateValidator = new DateValidatorUsingDateTimeFormatter(dtf);
        if (dateValidator.isValid(dateStr)) {
            return LocalDate.parse(dateStr);
        } else throw new MealServiceException("Wrong data format (should be yyyy-MM-dd)");
    }


}
