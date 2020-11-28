package com.agh.riceitapi.service;

import com.agh.riceitapi.dto.RegisterDTO;
import com.agh.riceitapi.exception.RegisterException;
import com.agh.riceitapi.model.Role;
import com.agh.riceitapi.model.RoleName;
import com.agh.riceitapi.model.User;
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

    public Boolean existsByUsername(String username){
        return this.userRepository.existsByUsername(username);
    }
    public Boolean existsByEmail(String email){
        return this.userRepository.existsByEmail(email);
    }

    public User createUser(RegisterDTO registerDTO){
        User user = new User();

        if(registerDTO.getPassword().length()<8){
            throw new RegisterException("Password is too short!");
        }

        user.setUsername(registerDTO.getUsername());
        user.setEmail(registerDTO.getEmail());
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));

        Role role = roleRepository.findByName(RoleName.ROLE_USER).orElseThrow(
                () -> new RegisterException("There is no [ROLE_USER] role"));
        user.setRoles(Collections.singleton(role));

        return this.userRepository.save(user);
    }
}
