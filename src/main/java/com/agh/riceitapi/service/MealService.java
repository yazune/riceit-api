package com.agh.riceitapi.service;

import com.agh.riceitapi.dto.*;
import com.agh.riceitapi.exception.*;
import com.agh.riceitapi.model.Food;
import com.agh.riceitapi.model.Meal;
import com.agh.riceitapi.model.User;
import com.agh.riceitapi.repository.FoodRepository;
import com.agh.riceitapi.repository.MealRepository;
import com.agh.riceitapi.repository.UserRepository;
import com.agh.riceitapi.util.DecimalOperator;
import com.agh.riceitapi.util.DateValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
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

    @Autowired
    private DayService dayService;

    public void createMeal(long userId, DateDTO dateDTO) throws UserNotFoundException{
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("There is no user with id: [" + userId + "]."));

        Meal meal = new Meal();

        LocalDate date = DateValidator.parseStrToLocalDate(dateDTO.getDate());
        meal.setDate(date);
        meal.createConnectionWithUser(user);

        mealRepository.save(meal);
    }

    public MealsDTO getMeals(long userId, DateDTO dateDTO){
        LocalDate date = DateValidator.parseStrToLocalDate(dateDTO.getDate());
        List<Meal> meals = mealRepository.findAllByUserIdAndDate(userId, date);
        return new MealsDTO(meals);
    }

    public void removeMeal(long userId, Long mealId) throws MealNotFoundException, PermissionDeniedException{
        Meal meal = mealRepository.findById(mealId).orElseThrow(
                () -> new MealNotFoundException("There is no meal with id: [" + mealId + "]."));

        if (meal.getUser().getId() != userId) throw new PermissionDeniedException("Permission denied.");

        meal.removeConnectionWithUser();
        mealRepository.delete(meal);

        dayService.removeMeal(userId, meal.getDate(), meal);
    }

    public void addFood(long userId, FoodAddDTO addFoodDTO) throws MealNotFoundException, PermissionDeniedException{
        Meal meal = mealRepository.findById(addFoodDTO.getMealId()).orElseThrow(
                () -> new MealNotFoundException("There is no meal with id: [" + addFoodDTO.getMealId() + "]."));

        if (meal.getUser().getId() != userId) throw new PermissionDeniedException("Permission denied.");

        Food food = new Food();

        double roundKcal = DecimalOperator.round(addFoodDTO.getKcal());
        double roundProt = DecimalOperator.round(addFoodDTO.getProtein());
        double roundFat = DecimalOperator.round(addFoodDTO.getFat());
        double roundCarb = DecimalOperator.round(addFoodDTO.getCarbohydrate());

        food.setName(addFoodDTO.getName());
        food.setKcal(roundKcal);
        food.setProtein(roundProt);
        food.setFat(roundFat);
        food.setCarbohydrate(roundCarb);

        meal.addFood(food);
        mealRepository.save(meal);
        dayService.addFood(userId, meal.getDate(), food);
    }

    public void updateFood(long userId, Long foodId, FoodDTO foodDTO) throws FoodNotFoundException, PermissionDeniedException, IOException {
        Food food = foodRepository.findById(foodId).orElseThrow(
                () -> new FoodNotFoundException("There is no food with id: [" + foodId + "]."));

        Meal meal = food.getMeal();
        if (meal.getUser().getId() != userId){
            throw new PermissionDeniedException("Permission denied.");
        }

        ObjectMapper objectMapper = new ObjectMapper();
        Food foodBeforeChanges = objectMapper.readValue(objectMapper.writeValueAsString(food), Food.class);

        meal.removeFood(food);

        food.setName(foodDTO.getName());
        food.setKcal(DecimalOperator.round(foodDTO.getKcal()));
        food.setProtein(DecimalOperator.round(foodDTO.getProtein()));
        food.setFat(DecimalOperator.round(foodDTO.getFat()));
        food.setCarbohydrate(DecimalOperator.round(foodDTO.getCarbohydrate()));

        meal.addFood(food);
        mealRepository.save(meal);

        dayService.updateFood(userId, meal.getDate(), foodBeforeChanges, food);
    }

    public void removeFood(long userId, Long foodId) throws FoodNotFoundException, PermissionDeniedException{
        Food food = foodRepository.findById(foodId).orElseThrow(
                () -> new FoodNotFoundException("There is no food with id: [" + foodId + "]."));
        Meal meal = food.getMeal();

        if (meal.getUser().getId() != userId){
            throw new PermissionDeniedException("Permission denied.");
        }

        meal.removeFood(food);
        mealRepository.save(meal);
        dayService.removeFood(userId, meal.getDate(), food);
    }

    public Food getFood(long userId, Long foodId) throws FoodNotFoundException, PermissionDeniedException{
        Food food = foodRepository.findById(foodId).orElseThrow(
                () -> new FoodNotFoundException("There is no food with id: [" + foodId + "]."));

        if (food.getMeal().getUser().getId() != userId){
            throw new PermissionDeniedException("Permission denied.");
        } else return food;
    }

    public Meal getMeal(long userId, Long mealId) throws MealNotFoundException, PermissionDeniedException{
        Meal meal = mealRepository.findById(mealId).orElseThrow(
                () -> new MealNotFoundException("There is no meal with id: [" + mealId + "]."));

        if (meal.getUser().getId() != userId){
            throw new PermissionDeniedException("Permission denied.");
        } else return meal;
    }

}
