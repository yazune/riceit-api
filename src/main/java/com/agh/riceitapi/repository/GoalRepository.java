package com.agh.riceitapi.repository;


import com.agh.riceitapi.model.Goal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GoalRepository extends JpaRepository<Goal, Long> {

    @Query("Select g.manParamsInUse FROM Goal g WHERE g.user.id = :userId")
    boolean areManParamsInUse(@Param("userId") long userId);
}
