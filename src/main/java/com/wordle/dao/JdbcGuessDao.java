package com.wordle.dao;

import com.wordle.logger.Logger;
import com.wordle.model.Guess;
import com.wordle.model.User;
import com.wordle.model.dto.GuessDto;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcGuessDao implements GuessDao{

    private JdbcTemplate jdbcTemplate;
    private LetterDao letterDao;

    public JdbcGuessDao(JdbcTemplate jdbcTemplate, LetterDao letterDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.letterDao = letterDao;
    }

    @Override
    public List<GuessDto> getGuessesByGameId(Integer gameId) {
        String sql =
                "SELECT guess_id, guess_number " +
                "FROM guess " +
                "WHERE game_id = ? " +
                "ORDER BY guess_number;";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, gameId);
        return guessDtoListMapper(rowSet);
    }

    @Override
    public GuessDto createGuess(GuessDto guess, Integer gameId) {
        String sql =
                "INSERT INTO guess (game_id, guess_number) " +
                "VALUES (?, ?) RETURNING guess_id;";
        Integer id = jdbcTemplate.queryForObject(sql, Integer.class, gameId, guess.getGuessNumber());
        return id != null ? getGuessById(id) : null;
    }

    @Override
    public GuessDto getGuessById(Integer guessId) {
        String sql =
                "SELECT guess_id, guess_number " +
                "FROM guess " +
                "WHERE guess_id = ? ";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, guessId);
        return rowSet.next() ? guessDtoMapper(rowSet) : null;
    }

    @Override
    public Integer getLastGuessIdByUser(User user) {
        String sql =
                "SELECT guess_id " +
                "FROM guess " +
                "JOIN game ON guess.game_id = game.game_id " +
                "WHERE game.user_id = ? " +
                "ORDER BY guess_number DESC " +
                "LIMIT 1;";
        return jdbcTemplate.queryForObject(sql, Integer.class, user.getId());
    }

    private List<GuessDto> guessDtoListMapper(SqlRowSet rowSet) {
        List<GuessDto> guesses = new ArrayList<>();
        while (rowSet.next()) {
            guesses.add(guessDtoMapper(rowSet));
        }
        return guesses;
    }

    private GuessDto guessDtoMapper(SqlRowSet rowSet) {
        try {
            GuessDto guess = new GuessDto();
            guess.setGuessNumber(rowSet.getInt("guess_number"));
            guess.setLetters(letterDao.getLetterByGuessId(rowSet.getInt("guess_id")));
            return guess;
        } catch (Exception e) {
            Logger.log(e);
        }
        return null;
    }

}
