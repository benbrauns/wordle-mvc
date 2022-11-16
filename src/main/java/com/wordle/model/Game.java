package com.wordle.model;

import java.time.LocalDate;

public class Game {
    private Integer gameId;
    private Integer userId;
    private LocalDate solution;

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

    public LocalDate getSolution() {
        return solution;
    }

    public void setSolution(LocalDate solution) {
        this.solution = solution;
    }
}
