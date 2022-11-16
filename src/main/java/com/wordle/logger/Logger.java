package com.wordle.logger;

public class Logger {
    public static void log(Exception e) {
        System.out.println(e.getMessage());
    }
}
