package com.agh.riceitapi.repository;

import com.agh.riceitapi.model.UserSettings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserSettingsRepository extends JpaRepository<UserSettings, Long> {

    Optional<UserSettings> findByUserId(Long userId);
}
