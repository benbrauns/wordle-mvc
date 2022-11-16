package com.wordle.dao;

import com.wordle.model.Game;
import com.wordle.model.User;
import com.wordle.model.dto.GameDto;

import java.time.LocalDate;

public interface GameDao {


    GameDto getGameDtoByUser(User user);
    GameDto createGameByUser(User user);
    Integer getCurrentGameIdByUser(User user);
}
