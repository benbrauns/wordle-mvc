package com.wordle.model;

import java.time.LocalDate;

public class Solution {
    private LocalDate gameDate;
    private String word;

    public LocalDate getGameDate() {
        return gameDate;
    }

    public void setGameDate(LocalDate gameDate) {
        this.gameDate = gameDate;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }
}
