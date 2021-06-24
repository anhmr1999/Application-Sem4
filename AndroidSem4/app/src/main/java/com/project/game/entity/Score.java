package com.project.game.entity;

public class Score{
    private Integer levelHardId;
    private Integer gameId;
    private Integer userId;
    private int score;
    private LevelHard levelHard;
    private Game game;
    private User user;

    public Score() {
    }

    public Score(Integer gameId, Integer userId, Integer levelHardId, int score) {
        this.gameId = gameId;
        this.userId = userId;
        this.score = score;
        this.levelHardId = levelHardId;
    }

    public Integer getLevelHardId() {
        return levelHardId;
    }

    public void setLevelHardId(Integer levelHardId) {
        this.levelHardId = levelHardId;
    }

    public Integer getGameId() {
        return gameId;
    }

    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public LevelHard getLevelHard() {
        return levelHard;
    }

    public void setLevelHard(LevelHard levelHard) {
        this.levelHard = levelHard;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
