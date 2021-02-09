package com.agh.riceitapi.service;

import com.agh.riceitapi.dto.UserDetailsGetDTO;
import com.agh.riceitapi.dto.UserDetailsDTO;
import com.agh.riceitapi.exception.UserDetailsNotFoundException;
import com.agh.riceitapi.model.UserDetails;
import com.agh.riceitapi.repository.UserDetailsRepository;
import com.agh.riceitapi.security.UserPrincipal;
import com.agh.riceitapi.util.DietParamCalculator;
import com.agh.riceitapi.util.Gender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsService {

    @Autowired
    private UserDetailsRepository userDetailsRepository;

    @Autowired
    private DayService dayService;

    public void updateUserDetails(long userId, UserDetailsDTO userDetailsDTO) throws UserDetailsNotFoundException{
        UserDetails userDetails = userDetailsRepository.findByUserId(userId).orElseThrow(
                () -> new UserDetailsNotFoundException("There is no user details with id: [" + userId + "]."));

        double height = userDetailsDTO.getHeight();
        double weight = userDetailsDTO.getWeight();
        int age = userDetailsDTO.getAge();
        Gender gender = Gender.valueOf(userDetailsDTO.getGender());
        double pal = userDetailsDTO.getPal();

        double bmr = DietParamCalculator.calculateBmr(height, weight, age, gender);

        userDetails.setHeight(height);
        userDetails.setWeight(weight);
        userDetails.setAge(age);
        userDetails.setGender(gender);
        userDetails.setPal(pal);
        userDetails.setBmr(bmr);

        userDetailsRepository.save(userDetails);
        dayService.updateLastDay(userId);
    }

    public UserDetailsGetDTO getUserDetails(UserPrincipal currentUser) throws UserDetailsNotFoundException{
        UserDetails userDetails =  userDetailsRepository.findByUserId(currentUser.getId()).orElseThrow(
                () -> new UserDetailsNotFoundException("There is no user details with id: [" + currentUser.getId() + "]."));

        return new UserDetailsGetDTO(currentUser, userDetails);
    }

}
