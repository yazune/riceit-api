package com.agh.riceitapi.model;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "days")
public class Day {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private LocalDate date;

    private double kcalToEat;
    private double proteinToEat;
    private double fatToEat;
    private double carbohydrateToEat;

    private double kcalConsumed = 0.0;
    private double proteinConsumed = 0.0;
    private double fatConsumed = 0.0;
    private double carbohydrateConsumed = 0.0;

    private double kcalBurnt = 0.0;

    public Day(){}

    public void fillWithDataFrom(Goal goal){
        if (goal.areManParamsInUse()){
            this.kcalToEat = goal.getManKcal();
            this.proteinToEat = goal.getManProtein();
            this.fatToEat = goal.getManFat();
            this.carbohydrateToEat = goal.getManCarbohydrate();
        } else {
            this.kcalToEat = goal.getAutoKcal();
            this.proteinToEat = goal.getAutoProtein();
            this.fatToEat = goal.getAutoFat();
            this.carbohydrateToEat = goal.getAutoCarbohydrate();
        }
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private User user;

    public void addKcalBurntFromActivity(Activity activity){
        this.kcalBurnt += activity.getKcalBurnt();
    }

    public void removeKcalBurntFromActivity(Activity activity){
        this.kcalBurnt -= activity.getKcalBurnt();
    }

    public void addMacroFromFood(Food food){
        this.kcalConsumed += food.getKcal();
        this.proteinConsumed += food.getProtein();
        this.fatConsumed += food.getFat();
        this.carbohydrateConsumed += food.getCarbohydrate();
    }

    public void removeMacroFromFood(Food food){
        this.kcalConsumed -= food.getKcal();
        this.proteinConsumed -= food.getProtein();
        this.fatConsumed -= food.getFat();
        this.carbohydrateConsumed -= food.getCarbohydrate();
    }


    public void removeMacroFromMeal(Meal meal){
        for(Food f : meal.getFoods()){
            removeMacroFromFood(f);
        }
    }

    public void createConnectionWithUser(User user){
        this.user = user;
        user.getDays().add(this);
    }

    public void removeConnectionWithUser(){
        user.getDays().remove(this);
        this.user = null;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getKcalToEat() {
        return kcalToEat;
    }

    public void setKcalToEat(double kcalToEat) {
        this.kcalToEat = kcalToEat;
    }

    public double getProteinToEat() {
        return proteinToEat;
    }

    public void setProteinToEat(double proteinToEat) {
        this.proteinToEat = proteinToEat;
    }

    public double getFatToEat() {
        return fatToEat;
    }

    public void setFatToEat(double fatToEat) {
        this.fatToEat = fatToEat;
    }

    public double getCarbohydrateToEat() {
        return carbohydrateToEat;
    }

    public void setCarbohydrateToEat(double carbohydrateToEat) {
        this.carbohydrateToEat = carbohydrateToEat;
    }

    public double getKcalConsumed() {
        return kcalConsumed;
    }

    public void setKcalConsumed(double kcalConsumed) {
        this.kcalConsumed = kcalConsumed;
    }

    public double getProteinConsumed() {
        return proteinConsumed;
    }

    public void setProteinConsumed(double proteinConsumed) {
        this.proteinConsumed = proteinConsumed;
    }

    public double getFatConsumed() {
        return fatConsumed;
    }

    public void setFatConsumed(double fatConsumed) {
        this.fatConsumed = fatConsumed;
    }

    public double getCarbohydrateConsumed() {
        return carbohydrateConsumed;
    }

    public void setCarbohydrateConsumed(double carbohydrateConsumed) {
        this.carbohydrateConsumed = carbohydrateConsumed;
    }

    public double getKcalBurnt() {
        return kcalBurnt;
    }

    public void setKcalBurnt(double kcalBurnt) {
        this.kcalBurnt = kcalBurnt;
    }
}
