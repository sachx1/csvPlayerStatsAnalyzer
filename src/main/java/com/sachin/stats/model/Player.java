package com.sachin.stats.model;

public record Player(String name, String team, Position position, int age, int appearances, int minutes, int goals,
        int assists) {

    public double goalsPer90() {
        if (minutes == 0) {
            return 0.0;
        }

        double average = goals * 90.0 / minutes;
        return average;
    }
}
