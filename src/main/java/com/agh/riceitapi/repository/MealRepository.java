package com.agh.riceitapi.repository;

import com.agh.riceitapi.model.Food;
import com.agh.riceitapi.model.Meal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface MealRepository extends JpaRepository<Meal, Long> {

    @Query("SELECT m FROM Meal m WHERE m.user.id = :userId AND m.date = :date")
    List<Meal> findAllByUserIdAndDate(@Param("userId") Long userId, @Param("date") LocalDate date);

    @Query("SELECT m FROM Meal m WHERE m.id = :mealId AND m.user.id = :userId")
    Optional<Meal> findByIdAndUserId(@Param("mealId") Long mealId, @Param("userId") Long userId);

}
