package com.project.game.entity;

public class LevelHard extends Entity{
    private String name;
    private String description;
    private Integer gameId;
    private Game game;

    public LevelHard(Integer id) {
        super(id);
    }

    public LevelHard(Integer id, String name, String description, Integer gameId) {
        super(id);
        this.name = name;
        this.description = description;
        this.gameId = gameId;
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

    public Integer getGameId() {
        return gameId;
    }

    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}
