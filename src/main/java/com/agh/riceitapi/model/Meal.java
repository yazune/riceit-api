package com.agh.riceitapi.model;


import com.agh.riceitapi.util.DecimalOperator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "meals")
public class Meal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private LocalDate date;

    private double kcal = 0.00;

    private double carbohydrate = 0.0;

    private double protein = 0.0;

    private double fat = 0.0;

    @OneToMany(
            mappedBy = "meal",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Food> foods = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private User user;

    public Meal(){}

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

    public double getKcal() {
        return kcal;
    }

    public void setKcal(double kcal) {
        this.kcal = kcal;
    }

    public double getCarbohydrate() {
        return carbohydrate;
    }

    public void setCarbohydrate(double carbohydrate) {
        this.carbohydrate = carbohydrate;
    }

    public double getProtein() {
        return protein;
    }

    public void setProtein(double protein) {
        this.protein = protein;
    }

    public double getFat() {
        return fat;
    }

    public void setFat(double fat) {
        this.fat = fat;
    }

    public List<Food> getFoods() {
        return foods;
    }

    public void setFoods(List<Food> foods) {
        this.foods = foods;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void addFood(Food food){
        this.foods.add(food);
        food.setMeal(this);

        double sumKcal = this.kcal + food.getKcal();
        double sumProt = this.protein + food.getProtein();
        double sumFat = this.fat + food.getFat();
        double sumCarb = this.carbohydrate + food.getCarbohydrate();

        this.kcal = DecimalOperator.round(sumKcal);
        this.fat = DecimalOperator.round(sumFat);
        this.protein = DecimalOperator.round(sumProt);
        this.carbohydrate = DecimalOperator.round(sumCarb);
    }

    public void removeFood(Food food){
        this.foods.remove(food);
        food.setMeal(null);

        double subKcal = this.kcal - food.getKcal();
        double subProt = this.protein - food.getProtein();
        double subFat = this.fat - food.getFat();
        double subCarb = this.carbohydrate - food.getCarbohydrate();

        this.kcal = DecimalOperator.round(subKcal);
        this.fat = DecimalOperator.round(subFat);
        this.protein = DecimalOperator.round(subProt);
        this.carbohydrate = DecimalOperator.round(subCarb);
    }

    public void createConnectionWithUser(User user){
        this.setUser(user);
        user.getMeals().add(this);
    }

    public void removeConnectionWithUser(){
        this.user.getMeals().remove(this);
        this.setUser(null);
    }

}
