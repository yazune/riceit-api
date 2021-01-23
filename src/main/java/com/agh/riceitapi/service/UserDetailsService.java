package com.agh.riceitapi.service;

import com.agh.riceitapi.dto.GetUserDetailsDTO;
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

    private DayService dayService;

    public void updateUserDetails(long userId, UserDetailsDTO userDetailsDTO) throws UserDetailsNotFoundException{
        UserDetails userDetails = userDetailsRepository.findByUserId(userId).orElseThrow(
                () -> new UserDetailsNotFoundException("There is no user details with id: [" + userId + "]."));

        double height = userDetailsDTO.getHeight();
        double weight = userDetailsDTO.getWeight();
        int age = userDetailsDTO.getAge();
        Gender gender = Gender.valueOf(userDetailsDTO.getGender());
        double k = userDetailsDTO.getK();

        double bmr = DietParamCalculator.calculateBmr(height, weight, age, gender);

        userDetails.setHeight(height);
        userDetails.setWeight(weight);
        userDetails.setAge(age);
        userDetails.setGender(gender);
        userDetails.setK(k);
        userDetails.setBmr(bmr);

        userDetailsRepository.save(userDetails);
        dayService.updateLastDay(userId);
    }

    public GetUserDetailsDTO getUserDetails(UserPrincipal currentUser) throws UserDetailsNotFoundException{
        UserDetails userDetails =  userDetailsRepository.findByUserId(currentUser.getId()).orElseThrow(
                () -> new UserDetailsNotFoundException("There is no user details with id: [" + currentUser.getId() + "]."));

        return new GetUserDetailsDTO(currentUser, userDetails);
    }

}
