package com.project.game.entity;

public class Achievement extends Entity{
    private String name;
    private String tutorial;
    private boolean checkScore;
    private int scoreOrNumber;
    private int gameId;
    private String levelName;

    public Achievement(Integer id) {
        super(id);
    }

    public Achievement(Integer id, String name, String tutorial, boolean checkScore, int scoreOrNumber,String levelName) {
        super(id);
        this.name = name;
        this.tutorial = tutorial;
        this.checkScore = checkScore;
        this.scoreOrNumber = scoreOrNumber;
        this.levelName = levelName;
    }

    public Achievement(Integer id, String name, String tutorial, boolean checkScore, int scoreOrNumber,String levelName, int gameId) {
        super(id);
        this.name = name;
        this.tutorial = tutorial;
        this.checkScore = checkScore;
        this.scoreOrNumber = scoreOrNumber;
        this.levelName = levelName;
        this.gameId = gameId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTutorial() {
        return tutorial;
    }

    public void setTutorial(String tutorial) {
        this.tutorial = tutorial;
    }

    public boolean isCheckScore() {
        return checkScore;
    }

    public void setCheckScore(boolean checkScore) {
        this.checkScore = checkScore;
    }

    public int getScoreOrNumber() {
        return scoreOrNumber;
    }

    public void setScoreOrNumber(int scoreOrNumber) {
        this.scoreOrNumber = scoreOrNumber;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }
}
