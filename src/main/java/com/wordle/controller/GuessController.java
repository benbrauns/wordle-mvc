package com.wordle.controller;

import com.wordle.dao.*;
import com.wordle.model.Guess;
import com.wordle.model.Solution;
import com.wordle.model.User;
import com.wordle.model.dto.GuessDto;
import com.wordle.model.dto.LetterDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/game/guess")
public class GuessController {

    private GuessDao guessDao;
    private GameDao gameDao;
    private LetterDao letterDao;
    private UserDao userDao;
    private SolutionDao solutionDao;

    public GuessController(GuessDao guessDao, GameDao gameDao, LetterDao letterDao, UserDao userDao, SolutionDao solutionDao) {
        this.guessDao = guessDao;
        this.gameDao = gameDao;
        this.letterDao = letterDao;
        this.userDao = userDao;
        this.solutionDao = solutionDao;
    }

    @PostMapping(path = "")
    public GuessDto guess(Principal principal, @Valid @RequestBody GuessDto guess) {
        User user = userDao.findByUsername(principal.getName());
        Integer gameId = gameDao.getCurrentGameIdByUser(user);
        GuessDto result = guessDao.createGuess(guess, gameId);
        int i = 0;
        StringBuilder solution = new StringBuilder(solutionDao.getSolutionByDate(LocalDate.now()).getWord());
        for (LetterDto letter : guess.getLetters()) {
            if (letter.getLetter().equalsIgnoreCase(solution.substring(i, i + 1))) {
                letter.setResult("green");
                solution.replace(i, i+1, "-");
            }
            i++;
        }
        for (LetterDto letter : guess.getLetters()) {
            if (!letter.getResult().isBlank()) { continue; }
            int index = solution.indexOf(letter.getLetter().toUpperCase(Locale.ROOT));
            if (index == -1) {
                letter.setResult("black");
            } else {
                letter.setResult("yellow");
                solution.replace(index, index + 1, "-");
            }
            letterDao.createLetter(letter, guessDao.getLastGuessIdByUser(user));
        }
        result.setLetters(guess.getLetters());
        return result;
    }


}
