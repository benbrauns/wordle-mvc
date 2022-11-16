package com.wordle.model.dto;

import com.wordle.model.Solution;
import com.wordle.model.User;

import java.util.List;

public class GameDto {
    private User user;
    private List<GuessDto> guesses;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<GuessDto> getGuesses() {
        return guesses;
    }

    public void setGuesses(List<GuessDto> guesses) {
        this.guesses = guesses;
    }
}
