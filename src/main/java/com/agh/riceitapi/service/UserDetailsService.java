package com.agh.riceitapi.service;


import com.agh.riceitapi.dto.GetUserDetailsDTO;
import com.agh.riceitapi.dto.UserDetailsDTO;
import com.agh.riceitapi.exception.UserDetailsNotFoundException;
import com.agh.riceitapi.exception.UserNotFoundException;
import com.agh.riceitapi.model.User;
import com.agh.riceitapi.model.UserDetails;
import com.agh.riceitapi.repository.UserDetailsRepository;
import com.agh.riceitapi.repository.UserRepository;
import com.agh.riceitapi.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsService {

    @Autowired
    private UserDetailsRepository userDetailsRepository;

    @Autowired
    private GoalService goalService;

    public void updateUserDetails(long userId, UserDetailsDTO userDetailsDTO) throws UserDetailsNotFoundException{
        UserDetails userDetails = userDetailsRepository.findByUserId(userId).orElseThrow(
                () -> new UserDetailsNotFoundException("There is no user details with id: [" + userId + "]."));

        userDetails.fillWithDataFrom(userDetailsDTO);
        userDetailsRepository.save(userDetails);

        goalService.autoCalculateParameters(userId, userDetails);
    }

    public GetUserDetailsDTO getUserDetails(UserPrincipal currentUser) throws UserDetailsNotFoundException{
        UserDetails userDetails =  userDetailsRepository.findByUserId(currentUser.getId()).orElseThrow(
                () -> new UserDetailsNotFoundException("There is no user details with id: [" + currentUser.getId() + "]."));

        return new GetUserDetailsDTO(currentUser, userDetails);
    }

}
