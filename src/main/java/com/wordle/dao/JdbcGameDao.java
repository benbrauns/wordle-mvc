package com.wordle.dao;

import com.wordle.logger.Logger;
import com.wordle.model.Game;
import com.wordle.model.User;
import com.wordle.model.dto.GameDto;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDate;

@Component
public class JdbcGameDao implements GameDao {

    private JdbcTemplate jdbcTemplate;
    private GuessDao guessDao;

    public JdbcGameDao(JdbcTemplate jdbcTemplate, GuessDao guessDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.guessDao = guessDao;
    }

    @Override
    public GameDto getGameDtoByUser(User user) {
        String sql =
                "SELECT game.game_id, game.user_id, game.solution " +
                "FROM game " +
                "JOIN wordle_user AS wu ON game.user_id = wu.user_id " +
                "WHERE game.solution = ? " +
                "AND wu.username = ?;";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, LocalDate.now(), user.getUsername());
        if (rowSet.next()) {
            return gameDtoMapper(rowSet, user);
        }
        return null;
    }

    @Override
    public GameDto createGameByUser(User user) {
        String sql =
                "INSERT INTO game (user_id, solution) " +
                "VALUES (?, ?);";
        jdbcTemplate.update(sql, user.getId(), LocalDate.now());
        return getGameDtoByUser(user);
    }

    @Override
    public Integer getCurrentGameIdByUser(User user) {
        String sql =
                "SELECT game_id " +
                "FROM game " +
                "WHERE user_id = ? " +
                "AND solution = ?;";
        return jdbcTemplate.queryForObject(sql, Integer.class, user.getId(), LocalDate.now());
    }

    private GameDto gameDtoMapper(SqlRowSet rowSet, User user) {
        try {
            GameDto game = new GameDto();
            game.setUser(user);
            game.setGuesses(guessDao.getGuessesByGameId(rowSet.getInt("game_id")));
            return game;
        } catch (Exception e) {
            Logger.log(e);
        }
        return null;
    }



    private Game gameMapper(SqlRowSet rowSet) {
        try {
            Game game = new Game();
            game.setGameId(rowSet.getInt("game_id"));
            game.setUserId(rowSet.getInt("user_id"));
            Date solution = rowSet.getDate("solution");
            if (solution != null) {
                game.setSolution(solution.toLocalDate());
            }
            return game;
        } catch (Exception e) {
            Logger.log(e);
        }
        return null;
    }

}
