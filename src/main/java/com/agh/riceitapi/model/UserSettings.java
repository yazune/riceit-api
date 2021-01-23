package com.agh.riceitapi.model;

import com.agh.riceitapi.util.DietType;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "user_settings")
public class UserSettings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private boolean useK = false;

    private boolean useManParameters = false;

    @Enumerated(EnumType.STRING)
    private DietType dietType = DietType.MAINTAINING;

    @OneToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private User user;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isUseK() {
        return useK;
    }

    public void setUseK(boolean useK) {
        this.useK = useK;
    }

    public boolean isUseManParameters() {
        return useManParameters;
    }

    public void setUseManParameters(boolean useManParameters) {
        this.useManParameters = useManParameters;
    }

    public DietType getDietType() {
        return dietType;
    }

    public void setDietType(DietType dietType) {
        this.dietType = dietType;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void createConnectionWithUser(User user){
        this.setUser(user);
        user.setUserSettings(this);
    }

    public void removeConnectionWithUser(){
        this.user.setUserSettings(null);
        this.setUser(null);
    }
}
