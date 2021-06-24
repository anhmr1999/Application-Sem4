package com.project.game.entity;

public class UserAchievement{
    private Integer userId;
    private Integer achievementId;
    private Achievement achievement;

    public UserAchievement(Integer userId, Integer achievementId) {
        this.userId = userId;
        this.achievementId = achievementId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getAchievementId() {
        return achievementId;
    }

    public void setAchievementId(Integer achievementId) {
        this.achievementId = achievementId;
    }

    public Achievement getAchievement() {
        return achievement;
    }

    public void setAchievement(Achievement achievement) {
        this.achievement = achievement;
    }

}
