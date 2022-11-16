package com.wordle.dao;

import com.wordle.model.Solution;

import java.time.LocalDate;

public interface SolutionDao {
    Solution getSolutionByDate(LocalDate date);
}
