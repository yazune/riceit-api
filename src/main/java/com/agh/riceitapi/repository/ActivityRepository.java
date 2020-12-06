package com.agh.riceitapi.repository;

import com.agh.riceitapi.model.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Repository
public interface ActivityRepository extends JpaRepository<Activity,Long> {

    @Query("SELECT a FROM Activity a WHERE a.user.id = :userId AND a.date = :date")
    List<Activity> findAllByUserIdAndDate(@Param("userId") Long userId, @Param("date") LocalDate date);

}
