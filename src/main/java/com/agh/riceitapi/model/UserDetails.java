package com.agh.riceitapi.model;

import com.agh.riceitapi.dto.UserDetailsDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "user_details")
public class UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private double height;

    private double weight;

    private int age;

    private String gender;

    private double k;

    @OneToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private User user;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void createConnectionWithUser(User user){
        this.setUser(user);
        user.setUserDetails(this);
    }

    public void removeConnectionWithUser(){
        this.user.setUserDetails(null);
        this.setUser(null);
    }
    public void fillWithDataFrom(UserDetailsDTO userDetailsDTO){
        this.height = userDetailsDTO.getHeight();
        this.weight = userDetailsDTO.getWeight();
        this.age = userDetailsDTO.getAge();
        this.gender = userDetailsDTO.getGender();
        this.k = userDetailsDTO.getK();
    }


}
