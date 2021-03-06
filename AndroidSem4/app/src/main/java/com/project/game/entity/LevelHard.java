package com.project.game.entity;

public class LevelHard extends Entity{
    private String name;
    private String description;

    public LevelHard(Integer id) {
        super(id);
    }

    public LevelHard(Integer id, String name, String description) {
        super(id);
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
