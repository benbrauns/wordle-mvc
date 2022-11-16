package com.wordle.dao;

import com.wordle.model.dto.LetterDto;

import java.util.List;

public interface LetterDao {
    List<LetterDto> getLetterByGuessId(Integer guessId);
    void createLetter(LetterDto letter, Integer guessId);
}
