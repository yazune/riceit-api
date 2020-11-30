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
        meal.setUser(user);
        LocalDate date = parseStrToLocalDate(dateDTO.getDate());
        meal.setDate(date);

        return this.mealRepository.save(meal);
    }

    public List<Meal> showAll(long userId, DateDTO dateDTO){
        LocalDate date = parseStrToLocalDate(dateDTO.getDate());
        return this.mealRepository.findAllByUserIdAndDate(userId, date);
    }

    public void removeMeal(long userId, RemoveMealDTO removeMealDTO){
        Meal meal = this.mealRepository.findByIdAndUserId(removeMealDTO.getMealId(), userId).orElseThrow(
                () -> new MealServiceException("There is no meal with id: [" + removeMealDTO.getMealId() + "]."));

        this.mealRepository.delete(meal);
    }

    public Meal addFood(long userId, AddFoodDTO foodDTO){
        Meal meal = this.mealRepository.findByIdAndUserId(foodDTO.getMealId(), userId).orElseThrow(
                () -> new MealServiceException("There is no meal with id: [" + foodDTO.getMealId() + "]."));

        Food food = new Food();
        food.setName(foodDTO.getName());
        food.setKcal(foodDTO.getKcal());
        food.setCarbohydrate(foodDTO.getCarbohydrate());
        food.setFat(foodDTO.getFat());
        food.setProtein(foodDTO.getProtein());

        meal.increaseMacroByFood(food);
        meal.getFoods().add(food);

        //this.foodRepository.save(food); <--- You don't have to use it with @OneToMany and JpaRepository
        return this.mealRepository.save(meal);
    }

    // TODO - check later if it's possible to update food with no touching the Meal objects
    public Meal updateFood(long userId, UpdateFoodDTO updateFoodDTO){
        Meal meal = this.mealRepository.findByIdAndUserId(updateFoodDTO.getMealId(), userId).orElseThrow(
                () -> new MealServiceException("There is no meal with id: [" + updateFoodDTO.getMealId() + "]."));

        Food food = this.foodRepository.findById(updateFoodDTO.getFoodId()).orElseThrow(
                () -> new MealServiceException("There is no food with id: [" + updateFoodDTO.getFoodId() + "]."));

        if (!meal.getFoods().contains(food)){
            throw new MealServiceException("The food with id: [" + food.getId() + "] doesn't belong to meal with id: [" + meal.getId() +"].");
        }

        int foodPosition = meal.getFoods().indexOf(food);
        meal.decreaseMacroByFood(food);

        food.setName(updateFoodDTO.getName());
        food.setKcal(updateFoodDTO.getKcal());
        food.setCarbohydrate(updateFoodDTO.getCarbohydrate());
        food.setFat(updateFoodDTO.getFat());
        food.setProtein(updateFoodDTO.getProtein());

        meal.increaseMacroByFood(food);
        meal.getFoods().set(foodPosition, food);

        return this.mealRepository.save(meal);
    }

    public Meal removeFood(long userId, RemoveFoodDTO removeFoodDTO){
        Meal meal = this.mealRepository.findByIdAndUserId(removeFoodDTO.getMealId(), userId).orElseThrow(
                () -> new MealServiceException("There is no meal with id: [" + removeFoodDTO.getMealId() + "]."));

        Food food = this.foodRepository.findById(removeFoodDTO.getFoodId()).orElseThrow(
                () -> new MealServiceException("There is no food with id: ]" + removeFoodDTO.getFoodId() + "]."));

        if (!meal.getFoods().contains(food)){
            throw new MealServiceException("The food with id: [" + food.getId() + "] doesn't belong to meal with id: [" + meal.getId() +"].");
        }

        meal.decreaseMacroByFood(food);
        meal.getFoods().remove(food);
//        this.foodRepository.delete(food); <<<---- test: you don't need it
        return this.mealRepository.save(meal);
    }

    public Food getFood(long userId, GetFoodDTO getFoodDTO){


        return this.foodRepository.findById(getFoodDTO.getFoodId()).orElseThrow(
                () -> new MealServiceException("There is no food with id: ]" + getFoodDTO.getFoodId() + "]."));
    }

    public LocalDate parseStrToLocalDate(String dateStr){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateValidator dateValidator = new DateValidatorUsingDateTimeFormatter(dtf);
        if (dateValidator.isValid(dateStr)) {
            return LocalDate.parse(dateStr);
        } else throw new MealServiceException("Wrong data format (should be yyyy-MM-dd)");
    }


}
