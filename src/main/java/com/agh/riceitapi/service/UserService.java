package com.agh.riceitapi.service;

import com.agh.riceitapi.dto.*;
import com.agh.riceitapi.exception.*;
import com.agh.riceitapi.model.*;
import com.agh.riceitapi.util.DietParamCalculator;
import com.agh.riceitapi.util.Gender;
import com.agh.riceitapi.util.RoleName;
import com.agh.riceitapi.repository.RoleRepository;
import com.agh.riceitapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UsernameDTO createUser(RegisterDTO registerDTO){

        if (userRepository.existsByUsername(registerDTO.getUsername())){
            throw new UserAlreadyExistsException("Username ["+ registerDTO.getUsername()+"] already exists.");
        }
        if (userRepository.existsByEmail(registerDTO.getEmail())){
            throw new EmailAlreadyExistsException("Email ["+ registerDTO.getEmail()+"] already exists.");
        }

        if(registerDTO.getPassword().length()<8){
            throw new RegisterException("Password is too short!");
        }

        User user = new User();
        user.setUsername(registerDTO.getUsername());
        user.setEmail(registerDTO.getEmail());
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));

        Role role = roleRepository.findByName(RoleName.ROLE_USER).orElseThrow(
                () -> new InternalServerException("There is no [ROLE_USER] role"));
        user.setRoles(Collections.singleton(role));

        UserDetails userDetails = new UserDetails();
        userDetails.setHeight(registerDTO.getHeight());
        userDetails.setWeight(registerDTO.getWeight());
        userDetails.setAge(registerDTO.getAge());
        userDetails.setK(registerDTO.getK());
        userDetails.setGender(Gender.valueOf(registerDTO.getGender()));

        double bmr = DietParamCalculator.calculateBmr(
                        registerDTO.getHeight(),
                        registerDTO.getWeight(),
                        registerDTO.getAge(),
                        Gender.valueOf(registerDTO.getGender()));

        userDetails.setBmr(bmr);
        userDetails.createConnectionWithUser(user);

        UserSettings userSettings = new UserSettings();
        userSettings.createConnectionWithUser(user);

        ManualParameters manualParameters = new ManualParameters();
        manualParameters.createConnectionWithUser(user);

        userRepository.save(user);

        return new UsernameDTO(user.getUsername());
    }

    public BooleanDTO existsByUsername(ExistsUsernameDTO existsUsernameDTO){

        BooleanDTO booleanDTO = new BooleanDTO();
        booleanDTO.setBool(userRepository.existsByUsername(existsUsernameDTO.getUsername()));
        return booleanDTO;
    }

    public BooleanDTO existsByEmail(ExistsEmailDTO existsEmailDTO){

        BooleanDTO booleanDTO = new BooleanDTO();
        booleanDTO.setBool(userRepository.existsByEmail(existsEmailDTO.getEmail()));
        return booleanDTO;
    }

    //method for testing OnCascade DELETE
    public void deleteUser(DeleteUserDTO deleteUserDTO){
        userRepository.deleteById(deleteUserDTO.getUserId());
    }
}
