package com.project.game.entity;

public class User extends Entity{
    private String name;
    private String accessToken;

    public User() {
        super(0);
    }

    public User(Integer id, String name, String accessToken) {
        super(id);
        this.name = name;
        this.accessToken = accessToken;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    @Override
    public String toString() {
        return "("+name+","+accessToken+")";
    }
}
