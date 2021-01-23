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

    private boolean useK = false;

    private double kcalBurnt = 0.0;
    private double proteinBurnt = 0.0;
    private double fatBurnt = 0.0;
    private double carbohydrateBurnt = 0.0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private User user;

    public Day(){}

    public void addMacroBurnt(double kcalBurnt, double proteinBurnt, double fatBurnt, double carbohydrateBurnt){
        this.kcalBurnt += kcalBurnt;
        this.proteinBurnt += proteinBurnt;
        this.fatBurnt += fatBurnt;
        this.carbohydrateBurnt += carbohydrateBurnt;
    }

    public void subtractMacroBurnt(double kcalBurnt, double proteinBurnt, double fatBurnt, double carbohydrateBurnt){
        this.kcalBurnt -= kcalBurnt;
        this.proteinBurnt -= proteinBurnt;
        this.fatBurnt -= fatBurnt;
        this.carbohydrateBurnt -= carbohydrateBurnt;
    }

    public void addKcalBurntFromSport(Sport sport){
        this.kcalBurnt += sport.getKcalBurnt();
    }

    public void removeKcalBurntFromSport(Sport sport){
        this.kcalBurnt -= sport.getKcalBurnt();
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

    public boolean isUseK() {
        return useK;
    }

    public void setUseK(boolean useK) {
        this.useK = useK;
    }

    public double getProteinBurnt() {
        return proteinBurnt;
    }

    public void setProteinBurnt(double proteinBurnt) {
        this.proteinBurnt = proteinBurnt;
    }

    public double getFatBurnt() {
        return fatBurnt;
    }

    public void setFatBurnt(double fatBurnt) {
        this.fatBurnt = fatBurnt;
    }

    public double getCarbohydrateBurnt() {
        return carbohydrateBurnt;
    }

    public void setCarbohydrateBurnt(double carbohydrateBurnt) {
        this.carbohydrateBurnt = carbohydrateBurnt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void createConnectionWithUser(User user){
        this.user = user;
        user.getDays().add(this);
    }

    public void removeConnectionWithUser(){
        user.getDays().remove(this);
        this.user = null;
    }
}
