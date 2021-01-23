package com.agh.riceitapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.IdentityHashMap;

@Entity
@Table(name = "manual_parameters")
public class ManualParameters {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private double manKcal = 0.0;
    private double manProtein = 0.0;
    private double manFat = 0.0;
    private double manCarbohydrate = 0.0;

    @OneToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private User user;

    public void createConnectionWithUser(User user){
        this.setUser(user);
        user.setManualParameters(this);
    }

    public void removeConnectionWithUser(){
        this.user.setManualParameters(null);
        this.setUser(null);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
}
