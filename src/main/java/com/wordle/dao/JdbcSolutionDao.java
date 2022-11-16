package com.wordle.dao;

import com.wordle.logger.Logger;
import com.wordle.model.Solution;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDate;

@Component
public class JdbcSolutionDao implements SolutionDao {
    private JdbcTemplate jdbcTemplate;

    public JdbcSolutionDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Solution getSolutionByDate(LocalDate date) {
        String sql =
                "SELECT game_date, word " +
                "FROM solution " +
                "WHERE game_date = ?;";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, date);
        if (rowSet.next()) {
            return solutionMapper(rowSet);
        }
        return null;
    }

    private Solution solutionMapper(SqlRowSet rowSet) {
        try {
            Solution solution = new Solution();
            Date gameDate = rowSet.getDate("game_date");
            if (gameDate != null) {
                solution.setGameDate(gameDate.toLocalDate());
            }
            solution.setWord(rowSet.getString("word"));
            return solution;
        } catch (Exception e) {
            Logger.log(e);
        }
        return null;
    }
}
