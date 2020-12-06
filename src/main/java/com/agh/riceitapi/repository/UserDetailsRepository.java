package com.agh.riceitapi.repository;


import com.agh.riceitapi.model.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDetailsRepository extends JpaRepository<UserDetails, Long> {

    @Query("SELECT ud FROM UserDetails ud WHERE ud.user.id = :userId")
    Optional<UserDetails> findByUserId(@Param("userId") long userId);

}
