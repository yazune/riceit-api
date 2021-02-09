package com.agh.riceitapi.service;

import com.agh.riceitapi.dto.UserSettingsDTO;
import com.agh.riceitapi.exception.UserSettingsNotFoundException;
import com.agh.riceitapi.model.UserSettings;
import com.agh.riceitapi.repository.UserSettingsRepository;
import com.agh.riceitapi.util.DietType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserSettingsService {


    @Autowired
    private UserSettingsRepository userSettingsRepository;

    @Autowired
    private DayService dayService;

    public UserSettingsDTO getUserSettings(Long userId){
        UserSettings us = userSettingsRepository.findByUserId(userId).orElseThrow(
                () -> new UserSettingsNotFoundException("There is no settings for user with id: " +userId));

        return new UserSettingsDTO(
                us.isUsePal(),
                us.isUseMan(),
                us.getDietType().name()
        );
    }

    public void updateUserSettings(Long userId, UserSettingsDTO dto){
        UserSettings us = userSettingsRepository.findByUserId(userId).orElseThrow(
                () -> new UserSettingsNotFoundException("There is no settings for user with id: " +userId));

        us.setUsePal(dto.isUsePal());
        us.setUseMan(dto.isUseMan());
        us.setDietType(DietType.valueOf(dto.getDietType()));

        userSettingsRepository.save(us);
        dayService.updateLastDay(userId);
    }

}
