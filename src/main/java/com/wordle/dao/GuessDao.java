package com.wordle.dao;

import com.wordle.model.User;
import com.wordle.model.dto.GuessDto;

import java.util.List;

public interface GuessDao {
    List<GuessDto> getGuessesByGameId(Integer gameId);
    GuessDto createGuess(GuessDto guess, Integer gameId);
    GuessDto getGuessById(Integer guessId);
    Integer getLastGuessIdByUser(User user);
}
