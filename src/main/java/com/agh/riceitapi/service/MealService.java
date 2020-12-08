package com.agh.riceitapi.service;

import com.agh.riceitapi.dto.*;
import com.agh.riceitapi.exception.*;
import com.agh.riceitapi.model.Food;
import com.agh.riceitapi.model.Meal;
import com.agh.riceitapi.model.User;
import com.agh.riceitapi.repository.FoodRepository;
import com.agh.riceitapi.repository.MealRepository;
import com.agh.riceitapi.repository.UserRepository;
import com.agh.riceitapi.validator.DateValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class MealService {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MealRepository mealRepository;

    @Autowired
    private FoodRepository foodRepository;

    public void createMeal(long userId, DateDTO dateDTO) throws UserNotFoundException{
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("There is no user with id: [" + userId + "]."));

        Meal meal = new Meal();

        LocalDate date = DateValidator.parseStrToLocalDate(dateDTO.getDate());
        meal.setDate(date);
        meal.createConnectionWithUser(user);

        mealRepository.save(meal);
    }

    public List<Meal> showAllMeals(long userId, DateDTO dateDTO){
        LocalDate date = DateValidator.parseStrToLocalDate(dateDTO.getDate());
        return mealRepository.findAllByUserIdAndDate(userId, date);
    }

    public void removeMeal(long userId, RemoveMealDTO removeMealDTO) throws MealNotFoundException, PermissionDeniedException{
        Meal meal = mealRepository.findById(removeMealDTO.getMealId()).orElseThrow(
                () -> new MealNotFoundException("There is no meal with id: [" + removeMealDTO.getMealId() + "]."));

        if (meal.getUser().getId() != userId) throw new PermissionDeniedException("Permission denied.");

        meal.removeConnectionWithUser();
        mealRepository.delete(meal);
    }

    public void addFood(long userId, AddFoodDTO addFoodDTO) throws MealNotFoundException, PermissionDeniedException{
        Meal meal = mealRepository.findById(addFoodDTO.getMealId()).orElseThrow(
                () -> new MealNotFoundException("There is no meal with id: [" + addFoodDTO.getMealId() + "]."));

        if (meal.getUser().getId() != userId) throw new PermissionDeniedException("Permission denied.");

        Food food = new Food();
        food.fillWithDataFrom(addFoodDTO);

        meal.addFood(food);
        mealRepository.save(meal);
    }

    public void updateFood(long userId, UpdateFoodDTO updateFoodDTO) throws FoodNotFoundException, PermissionDeniedException{
        Food food = foodRepository.findById(updateFoodDTO.getFoodId()).orElseThrow(
                () -> new FoodNotFoundException("There is no food with id: [" + updateFoodDTO.getFoodId() + "]."));
        Meal meal = food.getMeal();

        if (meal.getUser().getId() != userId){
            throw new PermissionDeniedException("Permission denied.");
        }

        meal.removeFood(food);

        food.fillWithDataFrom(updateFoodDTO);
        meal.addFood(food);

        mealRepository.save(meal);
    }

    public void removeFood(long userId, RemoveFoodDTO removeFoodDTO) throws FoodNotFoundException, PermissionDeniedException{
        Food food = foodRepository.findById(removeFoodDTO.getFoodId()).orElseThrow(
                () -> new FoodNotFoundException("There is no food with id: [" + removeFoodDTO.getFoodId() + "]."));
        Meal meal = food.getMeal();

        if (meal.getUser().getId() != userId){
            throw new PermissionDeniedException("Permission denied.");
        }

        meal.removeFood(food);
        mealRepository.save(meal);
    }

    public Food getFood(long userId, GetFoodDTO getFoodDTO) throws FoodNotFoundException, PermissionDeniedException{
        Food food = foodRepository.findById(getFoodDTO.getFoodId()).orElseThrow(
                () -> new FoodNotFoundException("There is no food with id: [" + getFoodDTO.getFoodId() + "]."));

        if (food.getMeal().getUser().getId() != userId){
            throw new PermissionDeniedException("Permission denied.");
        } else return food;
    }

}
