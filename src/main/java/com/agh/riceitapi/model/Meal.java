package com.agh.riceitapi.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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

    private double kcal = 0.0;

    private double carbohydrate = 0.0;

    private double protein = 0.0;

    private double fat = 0.0;

//    old one - unidirectional
//    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
//    @JoinColumn(name = "meal_id")
//    private List<Food> foods = new ArrayList<>();

    @OneToMany(
            mappedBy = "meal",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Food> foods = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE) //TODO - check if deleting the meal deletes an user #hopenot
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

        this.kcal += food.getKcal();
        this.carbohydrate += food.getCarbohydrate();
        this.fat += food.getFat();
        this.protein += food.getProtein();
    }
    public void removeFood(Food food){
        this.foods.remove(food);
        food.setMeal(null);

        this.kcal -= food.getKcal();
        this.carbohydrate -= food.getCarbohydrate();
        this.fat -= food.getFat();
        this.protein -= food.getProtein();
    }
}
