package com.project.game.adapter;

public class ScoreModel {
    private int id;
    private String name;
    private int easyScore;
    private int normalScore;
    private int difficultScore;

    public ScoreModel(int id, String name, int easyScore, int normalScore, int difficultScore) {
        this.id = id;
        this.name = name;
        this.easyScore = easyScore;
        this.normalScore = normalScore;
        this.difficultScore = difficultScore;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getEasyScore() {
        return easyScore;
    }

    public int getNormalScore() {
        return normalScore;
    }

    public int getDifficultScore() {
        return difficultScore;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEasyScore(int easyScore) {
        this.easyScore = easyScore;
    }

    public void setNormalScore(int normalScore) {
        this.normalScore = normalScore;
    }

    public void setDifficultScore(int difficultScore) {
        this.difficultScore = difficultScore;
    }
}
