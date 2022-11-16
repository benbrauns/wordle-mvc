package com.wordle.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

public class Guess {
    private Integer guessId;
    @NotNull
    private Integer gameId;
    @NotNull
    @Size(min = 1, max = 6)
    private Integer guessNumber;

    public Integer getGuessId() {
        return guessId;
    }

    public void setGuessId(Integer guessId) {
        this.guessId = guessId;
    }

    public Integer getGameId() {
        return gameId;
    }

    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }

    public Integer getGuessNumber() {
        return guessNumber;
    }

    public void setGuessNumber(Integer guessNumber) {
        this.guessNumber = guessNumber;
    }
}
