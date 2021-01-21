package com.agh.riceitapi.model;


import com.agh.riceitapi.model.util.SportConstants;
import com.agh.riceitapi.model.util.SportType;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "sports")
public class Sport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private LocalDate date;

    private String name;

    private int duration;

    private double kcalBurnt;

    @Enumerated(EnumType.STRING)
    private SportType sportType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private User user;

    public Sport(){}

    public void calculateKcalBurnt(double weight){
        String type = sportType.name();
        SportConstants ac = SportConstants.valueOf(type);
        this.kcalBurnt = ac.burntPerKg * (duration/60) * weight;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public SportType getSportType() {
        return sportType;
    }

    public void setSportType(SportType sportType) {
        this.sportType = sportType;
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

    public void setDate(LocalDate localDate) {
        this.date = localDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getKcalBurnt() {
        return kcalBurnt;
    }

    public void setKcalBurnt(double kcalBurnt) {
        this.kcalBurnt = kcalBurnt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void createConnectionWithUser(User user){
        this.setUser(user);
        user.getSports().add(this);
    }

    public void removeConnectionWithUser(){
        this.user.getSports().remove(this);
        this.setUser(null);
    }
}
