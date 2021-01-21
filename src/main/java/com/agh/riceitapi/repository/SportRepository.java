package com.agh.riceitapi.repository;

import com.agh.riceitapi.model.Sport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;


@Repository
public interface SportRepository extends JpaRepository<Sport,Long> {

    @Query("SELECT a FROM Sport a WHERE a.user.id = :userId AND a.date = :date")
    List<Sport> findAllByUserIdAndDate(@Param("userId") Long userId, @Param("date") LocalDate date);

}
