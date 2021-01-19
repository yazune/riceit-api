package com.agh.riceitapi.dto;

import com.agh.riceitapi.model.UserDetails;
import com.agh.riceitapi.security.UserPrincipal;

public class GetUserDetailsDTO {

    private String username;
    private String email;

    private double height;
    private double weight;
    private int age;
    private String gender;
    private double k;

    public GetUserDetailsDTO(UserPrincipal currentUser, UserDetails userDetails){
        this.username = currentUser.getUsername();
        this.email = currentUser.getEmail();

        this.height = userDetails.getHeight();
        this.weight = userDetails.getWeight();
        this.age = userDetails.getAge();
        this.gender = userDetails.getGender().name();
        this.k = userDetails.getK();
    }

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

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public double getK() {
        return k;
    }

    public void setK(double k) {
        this.k = k;
    }
}
