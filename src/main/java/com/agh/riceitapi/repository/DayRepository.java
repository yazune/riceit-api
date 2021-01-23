package com.agh.riceitapi.repository;


import com.agh.riceitapi.model.Day;
import com.agh.riceitapi.model.Meal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface DayRepository extends JpaRepository<Day, Long> {


    @Query("SELECT d FROM Day d WHERE d.user.id = :userId AND d.date = :date")
    Optional<Day> findByUserIdAndDate(@Param("userId") Long userId, @Param("date") LocalDate date);

    Optional<Day> findTopByUserIdOrderByDateDesc(Long userId);
}
