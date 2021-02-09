package com.agh.riceitapi.service;

import com.agh.riceitapi.dto.*;
import com.agh.riceitapi.exception.SportNotFoundException;
import com.agh.riceitapi.exception.PermissionDeniedException;
import com.agh.riceitapi.exception.UserNotFoundException;
import com.agh.riceitapi.model.Sport;
import com.agh.riceitapi.model.User;
import com.agh.riceitapi.util.SportType;
import com.agh.riceitapi.repository.SportRepository;
import com.agh.riceitapi.repository.UserRepository;
import com.agh.riceitapi.util.DateValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;

@Service
public class SportService {

    @Autowired
    private SportRepository sportRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DayService dayService;

    public void addSport(long userId, SportAddDTO sportAddDTO) throws UserNotFoundException{

        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("There is no user with id: [" + userId + "]."));

        Sport sport = new Sport();

        LocalDate date = DateValidator.parseStrToLocalDate(sportAddDTO.getDate());
        sport.setDate(date);
        sport.setName(sportAddDTO.getName());
        sport.setDuration(sportAddDTO.getDuration());
        sport.setSportType(SportType.valueOf(sportAddDTO.getSportType()));

        if (sportAddDTO.getKcalBurnt() >= 0){
            sport.setKcalBurnt(sportAddDTO.getKcalBurnt());
        } else {
            sport.calculateKcalBurnt(user.getUserDetails().getBmr(), user.getUserDetails().getWeight());
        }

        sport.createConnectionWithUser(user);
        sportRepository.save(sport);

        dayService.addSport(userId, date, sport);
    }

    public void updateSport(long userId, Long sportId, SportUpdateDTO sportUpdateDTO) throws SportNotFoundException, PermissionDeniedException, IOException {

        Sport sport = sportRepository.findById(sportId).orElseThrow(
                () -> new SportNotFoundException("There is no sport with id: [" + sportId + "]."));

        if (sport.getUser().getId() != userId){
            throw new PermissionDeniedException("Permission denied.");
        }

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        Sport sportBeforeChanges = objectMapper.readValue(objectMapper.writeValueAsString(sport), Sport.class);

        sport.setName(sportUpdateDTO.getName());
        sport.setDuration(sportUpdateDTO.getDuration());
        sport.setSportType(SportType.valueOf(sportUpdateDTO.getSportType()));

        if (sportUpdateDTO.getKcalBurnt() >= 0){
            sport.setKcalBurnt(sportUpdateDTO.getKcalBurnt());
        } else {
            sport.calculateKcalBurnt(sport.getUser().getUserDetails().getBmr(), sport.getUser().getUserDetails().getWeight());
        }

        sportRepository.save(sport);

        dayService.updateSport(userId, sport.getDate(), sportBeforeChanges, sport);
    }

    public void removeSport(long userId, Long sportId) throws SportNotFoundException, PermissionDeniedException{
        Sport sport = sportRepository.findById(sportId).orElseThrow(
                () -> new SportNotFoundException("There is no sport with id: [" + sportId + "]."));

        if (sport.getUser().getId() != userId){
            throw new PermissionDeniedException("Permission denied.");
        }

        sport.removeConnectionWithUser();
        sportRepository.delete(sport);

        dayService.removeSport(userId, sport.getDate(), sport);
    }

    public Sport getSport(long userId, Long sportId) throws SportNotFoundException, PermissionDeniedException{
        Sport sport = sportRepository.findById(sportId).orElseThrow(
                () -> new SportNotFoundException("There is no sport with id: [" + sportId + "]."));

        if (sport.getUser().getId() != userId){
            throw new PermissionDeniedException("Permission denied.");
        }

        return sport;
    }

    public SportsDTO getSports(long userId, DateDTO dateDTO){
        LocalDate date = DateValidator.parseStrToLocalDate(dateDTO.getDate());
        return new SportsDTO(sportRepository.findAllByUserIdAndDate(userId, date));
    }

}
