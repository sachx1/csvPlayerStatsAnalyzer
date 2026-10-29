package com.sachin.stats.model;

public record Player(String name, String team, Position position, int age, int appearances, int minutes, int goals,
        int assists) {

    public double goalsPer90() {
        double average = 0.0;
        if (minutes == 0) {
            average = 0.0;
        } else {
            average = goals * 90.0 / minutes;
        }

        return average;
    }
}
