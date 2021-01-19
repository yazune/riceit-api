package com.agh.riceitapi.dto;

import com.agh.riceitapi.model.Goal;

public class GetGoalDTO {
    
    private double autoKcal;
    private double autoProtein;
    private double autoFat;
    private double autoCarbohydrate;

    private boolean manParamsInUse;

    private String dietType;

    private double manKcal;
    private double manProtein;
    private double manFat;
    private double manCarbohydrate;

    public void fillDataWith(Goal goal){
        this.autoKcal = goal.getAutoKcal();
        this.autoProtein = goal.getAutoProtein();
        this.autoFat = goal.getAutoFat();
        this.autoCarbohydrate = goal.getAutoCarbohydrate();

        this.manParamsInUse = goal.areManParamsInUse();

        this.dietType = goal.getDietType().name();

        this.manKcal = goal.getManKcal();
        this.manProtein = goal.getManProtein();
        this.manFat = goal.getManFat();
        this.manCarbohydrate = goal.getManCarbohydrate();
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

    public boolean isManParamsInUse() {
        return manParamsInUse;
    }

    public void setManParamsInUse(boolean manParamsInUse) {
        this.manParamsInUse = manParamsInUse;
    }

    public String getDietType() {
        return dietType;
    }

    public void setDietType(String dietType) {
        this.dietType = dietType;
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
}
