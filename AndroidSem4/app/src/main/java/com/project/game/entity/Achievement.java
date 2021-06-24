package com.project.game.entity;

public class Achievement extends Entity{
    private String name;
    private String tutorial;

    public Achievement(Integer id, String name, String tutorial) {
        super(id);
        this.name = name;
        this.tutorial = tutorial;
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

}
