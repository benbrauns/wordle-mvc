package com.wordle.dao;

import com.wordle.logger.Logger;
import com.wordle.model.dto.LetterDto;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcLetterDao implements LetterDao{

    private JdbcTemplate jdbcTemplate;

    public JdbcLetterDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<LetterDto> getLetterByGuessId(Integer guessId) {
        String sql =
                "SELECT position, result " +
                "FROM letter " +
                "WHERE guess_id = ? " +
                "ORDER BY position;";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, guessId);
        return letterDtoListMapper(rowSet);
    }

    @Override
    public void createLetter(LetterDto letter, Integer guessId) {
        String sql =
                "INSERT INTO letter (guess_id, letter, position, result) " +
                "VALUES (?, ?, ?, ?);";
        jdbcTemplate.update(sql, guessId, letter.getLetter(), letter.getPosition(), letter.getResult());
    }

    private List<LetterDto> letterDtoListMapper(SqlRowSet rowSet) {
        List<LetterDto> letters = new ArrayList<>();
        while (rowSet.next()) {
            letters.add(letterDtoMapper(rowSet));
        }
        return letters;
    }

    private LetterDto letterDtoMapper(SqlRowSet rowSet) {
        try {
            LetterDto letter = new LetterDto();
            letter.setPosition(rowSet.getInt("position"));
            letter.setLetter(rowSet.getString("letter"));
            return letter;
        } catch (Exception e) {
            Logger.log(e);
        }
        return null;
    }
}
