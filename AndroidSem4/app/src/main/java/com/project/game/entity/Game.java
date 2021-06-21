package com.project.game.entity;

import java.util.List;

public class Game extends Entity{
    private String name;
    private List<LevelHard> levelHards;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<LevelHard> getLevelHards() {
        return levelHards;
    }

    public void setLevelHards(List<LevelHard> levelHards) {
        this.levelHards = levelHards;
    }
}
