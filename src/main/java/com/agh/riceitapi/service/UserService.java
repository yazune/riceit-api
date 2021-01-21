package com.agh.riceitapi.service;

import com.agh.riceitapi.dto.*;
import com.agh.riceitapi.exception.*;
import com.agh.riceitapi.model.*;
import com.agh.riceitapi.model.util.RoleName;
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
            //creating User
        User user = new User();
        user.setUsername(registerDTO.getUsername());
        user.setEmail(registerDTO.getEmail());
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));

        Role role = roleRepository.findByName(RoleName.ROLE_USER).orElseThrow(
                () -> new InternalServerException("There is no [ROLE_USER] role"));
        user.setRoles(Collections.singleton(role));

            //creating UserDetails and connecting with User
        UserDetails userDetails = new UserDetails();
        userDetails.fillWithDataFrom(registerDTO);
        userDetails.createConnectionWithUser(user);

            //creating Goal and connecting with User
        Goal goal = new Goal();
        goal.calculateParameters(userDetails);
        goal.createConnectionWithUser(user);

        userRepository.save(user);

        UsernameDTO usernameDTO = new UsernameDTO(user.getUsername());
        return usernameDTO;
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
