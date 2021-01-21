package com.agh.riceitapi.service;

import com.agh.riceitapi.dto.*;
import com.agh.riceitapi.exception.SportNotFoundException;
import com.agh.riceitapi.exception.PermissionDeniedException;
import com.agh.riceitapi.exception.UserNotFoundException;
import com.agh.riceitapi.model.Sport;
import com.agh.riceitapi.model.User;
import com.agh.riceitapi.model.util.SportType;
import com.agh.riceitapi.repository.SportRepository;
import com.agh.riceitapi.repository.UserRepository;
import com.agh.riceitapi.validator.DateValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Service
public class SportService {

    @Autowired
    private SportRepository sportRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DayService dayService;

    public void addSportMan(long userId, AddSportManDTO addSportManDTO) throws UserNotFoundException{

        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("There is no user with id: [" + userId + "]."));

        Sport sport = new Sport();

        LocalDate date = DateValidator.parseStrToLocalDate(addSportManDTO.getDate());
        sport.setDate(date);
        sport.setName(addSportManDTO.getName());
        sport.setKcalBurnt(addSportManDTO.getKcalBurnt());
        sport.setDuration(addSportManDTO.getDuration());
        sport.setSportType(SportType.valueOf(addSportManDTO.getSportType()));

        sport.createConnectionWithUser(user);
        sportRepository.save(sport);

        dayService.addSport(userId, date, sport);
    }

    public void addSportAuto(long userId, AddSportAutoDTO addSportAutoDTO) throws UserNotFoundException{

        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("There is no user with id: [" + userId + "]."));

        Sport sport = new Sport();

        LocalDate date = DateValidator.parseStrToLocalDate(addSportAutoDTO.getDate());
        sport.setDate(date);
        sport.setName(addSportAutoDTO.getName());
        sport.setDuration(addSportAutoDTO.getDuration());
        sport.setSportType(SportType.valueOf(addSportAutoDTO.getSportType()));

        sport.calculateKcalBurnt(user.getUserDetails().getWeight());

        sport.createConnectionWithUser(user);
        sportRepository.save(sport);

        dayService.addSport(userId, date, sport);
    }

    public void updateSport(long userId, UpdateSportDTO updateSportDTO) throws SportNotFoundException, PermissionDeniedException, IOException {

        Sport sport = sportRepository.findById(updateSportDTO.getSportId()).orElseThrow(
                () -> new SportNotFoundException("There is no sport with id: [" + updateSportDTO.getSportId() + "]."));

        if (sport.getUser().getId() != userId){
            throw new PermissionDeniedException("Permission denied.");
        }

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        Sport sportBeforeChanges = objectMapper.readValue(objectMapper.writeValueAsString(sport), Sport.class);

        sport.setName(updateSportDTO.getName());
        sport.setDuration(updateSportDTO.getDuration());
        sport.setSportType(SportType.valueOf(updateSportDTO.getSportType()));
        sport.setKcalBurnt(updateSportDTO.getKcalBurnt());

        sportRepository.save(sport);

        dayService.updateSport(userId, sport.getDate(), sportBeforeChanges, sport);
    }

    public void removeSport(long userId, RemoveSportDTO removeSportDTO) throws SportNotFoundException, PermissionDeniedException{
        Sport sport = sportRepository.findById(removeSportDTO.getSportId()).orElseThrow(
                () -> new SportNotFoundException("There is no sport with id: [" + removeSportDTO.getSportId() + "]."));

        if (sport.getUser().getId() != userId){
            throw new PermissionDeniedException("Permission denied.");
        }

        sport.removeConnectionWithUser();
        sportRepository.delete(sport);

        dayService.removeSport(userId, sport.getDate(), sport);
    }

    public Sport getSport(long userId, GetSportDTO getSportDTO) throws SportNotFoundException, PermissionDeniedException{
        Sport sport = sportRepository.findById(getSportDTO.getSportId()).orElseThrow(
                () -> new SportNotFoundException("There is no sport with id: [" + getSportDTO.getSportId() + "]."));

        if (sport.getUser().getId() != userId){
            throw new PermissionDeniedException("Permission denied.");
        }

        return sport;
    }

    public AllSportsDTO showAllSports(long userId, DateDTO dateDTO){
        LocalDate date = DateValidator.parseStrToLocalDate(dateDTO.getDate());
        return new AllSportsDTO(sportRepository.findAllByUserIdAndDate(userId, date));
    }

}
