package com.agh.riceitapi.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class RegisterDTO extends UserDetailsDTO{

    @NotBlank(message = "Username can not be empty.")
    @Size(min = 2, max = 45, message = "Username should be between 2 and 45 characters.")
    private String username;

    @NotBlank(message = "Email can not be empty.")
    @Email
    @Size(max = 45, message = "Wrong email format.")
    private String email;

    @NotBlank(message = "Password can not be empty.")
    @Size(min = 8, max = 45, message = "Password should be between 8 and 45 characters.")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}