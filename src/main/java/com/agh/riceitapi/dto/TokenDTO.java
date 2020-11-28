package com.agh.riceitapi.dto;

public class TokenDTO {

    private String type = "Bearer";
    private String token;

    public TokenDTO(String type, String token) {
        this.type = type;
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
