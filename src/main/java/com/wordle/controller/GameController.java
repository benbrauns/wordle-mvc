package com.wordle.controller;

import com.wordle.dao.GameDao;
import com.wordle.dao.UserDao;
import com.wordle.model.Game;
import com.wordle.model.dto.GameDto;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.time.LocalDate;

@RestController
@CrossOrigin(origins = {"http://127.0.0.1:5500", "https://benbrauns.github.io"})
@RequestMapping("/game")
public class GameController {

    private GameDao gameDao;
    private UserDao userDao;

    public GameController(GameDao gameDao, UserDao userDao) {
        this.gameDao = gameDao;
        this.userDao = userDao;
    }


    @RequestMapping(path = "", method = RequestMethod.GET)
    public GameDto getGame(Principal principal) {
        GameDto game = gameDao.getGameDtoByUser(userDao.findByUsername(principal.getName()));
        if (game == null) {
            return gameDao.createGameByUser(userDao.findByUsername(principal.getName()));
        }
        return game;
    }

}
