package com.wordle.model.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

public class GuessDto {
    @NotNull
    private Integer guessNumber;
    @NotNull
    private List<LetterDto> letters;

    public Integer getGuessNumber() {
        return guessNumber;
    }

    public void setGuessNumber(Integer guessNumber) {
        this.guessNumber = guessNumber;
    }

    public List<LetterDto> getLetters() {
        return letters;
    }

    public void setLetters(List<LetterDto> letters) {
        this.letters = letters;
    }
}
