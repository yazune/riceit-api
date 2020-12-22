package com.agh.riceitapi.model;

import com.agh.riceitapi.dto.UpdateGoalDTO;
import com.agh.riceitapi.exception.InternalServerException;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "goals")
public class Goal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private double autoKcal = 0.0;
    private double autoProtein = 0.0;
    private double autoFat = 0.0;
    private double autoCarbohydrate = 0.0;

    private boolean manParamsInUse = false;

    @Enumerated(EnumType.STRING)
    private DietType type = DietType.MAINTAINING;

    private double manKcal = 0.0;
    private double manProtein = 0.0;
    private double manFat = 0.0;
    private double manCarbohydrate = 0.0;

    @OneToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private User user;

    public Goal(){}

    public void calculateParameters(UserDetails userDetails){

        double bmr;

        if (userDetails.getGender().equals(Gender.MALE)){
            bmr = 66.4730 + (13.7516 * userDetails.getWeight()) + (5.0033 * userDetails.getHeight()) - (6.7750 * userDetails.getAge());
        } else if (userDetails.getGender().equals(Gender.FEMALE)){
            bmr = 665.0955 + (9.5634 * userDetails.getWeight()) + (1.8496 * userDetails.getHeight()) - (4.6756 * userDetails.getAge());
        } else throw new InternalServerException("Goal.calculateParameters: wrong Gender format!");


        if (this.type.equals(DietType.REDUCTION)){
            bmr -= 500.0;
        } else if(this.type.equals(DietType.GAIN)){
            bmr += 500.0;
        }
        this.autoKcal = bmr * userDetails.getK();
        this.autoProtein = this.autoKcal * 15 / 100 / 4;
        this.autoFat = this.autoKcal * 30 / 100 / 9;
        this.autoCarbohydrate = this.autoKcal * 55 / 100 / 4;
    }

    public void updateManualParameters(UpdateGoalDTO updateGoalDTO){
        this.manKcal = updateGoalDTO.getManKcal();
        this.manProtein = updateGoalDTO.getManProtein();
        this.manFat = updateGoalDTO.getManFat();
        this.manCarbohydrate = updateGoalDTO.getManCarbohydrate();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getAutoKcal() {
        return autoKcal;
    }

    public void setAutoKcal(double autoKcal) {
        this.autoKcal = autoKcal;
    }

    public double getAutoProtein() {
        return autoProtein;
    }

    public void setAutoProtein(double autoProtein) {
        this.autoProtein = autoProtein;
    }

    public double getAutoFat() {
        return autoFat;
    }

    public void setAutoFat(double autoFat) {
        this.autoFat = autoFat;
    }

    public double getAutoCarbohydrate() {
        return autoCarbohydrate;
    }

    public void setAutoCarbohydrate(double autoCarbohydrate) {
        this.autoCarbohydrate = autoCarbohydrate;
    }

    public boolean areManParamsInUse() {
        return manParamsInUse;
    }

    public void setManParamsInUse(boolean manParamsInUse) {
        this.manParamsInUse = manParamsInUse;
    }

    public double getManKcal() {
        return manKcal;
    }

    public void setManKcal(double manKcal) {
        this.manKcal = manKcal;
    }

    public double getManProtein() {
        return manProtein;
    }

    public void setManProtein(double manProtein) {
        this.manProtein = manProtein;
    }

    public double getManFat() {
        return manFat;
    }

    public void setManFat(double manFat) {
        this.manFat = manFat;
    }

    public double getManCarbohydrate() {
        return manCarbohydrate;
    }

    public void setManCarbohydrate(double manCarbohydrate) {
        this.manCarbohydrate = manCarbohydrate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void createConnectionWithUser(User user){
        this.setUser(user);
        user.setGoal(this);
    }

    public void removeConnectionWithUser(){
        this.user.setGoal(null);
        this.setUser(null);
    }

    public boolean isManParamsInUse() {
        return manParamsInUse;
    }

    public DietType getType() {
        return type;
    }

    public void setType(DietType type) {
        this.type = type;
    }


}
