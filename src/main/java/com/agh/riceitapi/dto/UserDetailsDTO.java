package com.agh.riceitapi.dto;

import javax.validation.constraints.NotBlank;

public class UserDetailsDTO {

    private double height;

    private double weight;

    @NotBlank
    private String gender;

    private int age;

    private double k;

    public UserDetailsDTO(){}

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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getK() {
        return k;
    }

    public void setK(double k) {
        this.k = k;
    }
}
