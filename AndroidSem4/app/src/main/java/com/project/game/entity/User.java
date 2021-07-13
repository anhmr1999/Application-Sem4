package com.project.game.entity;

import java.util.List;

public class User extends Entity{
    private String name;
    private String accessToken;
    private int avatar;
    private List<Achievement> achievements;
    private List<Score> scores;

    public User() {
        super(0);
    }

    public User(Integer id, String name, String accessToken, int avatar) {
        super(id);
        this.name = name;
        this.accessToken = accessToken;
        this.avatar = avatar;
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

    public int getAvatar() {
        return avatar;
    }

    public void setAvatar(int avatar) {
        this.avatar = avatar;
    }

    public List<Achievement> getAchievements() {
        return achievements;
    }

    public void setAchievements(List<Achievement> achievements) {
        this.achievements = achievements;
    }

    public List<Score> getScores() {
        return scores;
    }

    public void setScores(List<Score> scores) {
        this.scores = scores;
    }
}
