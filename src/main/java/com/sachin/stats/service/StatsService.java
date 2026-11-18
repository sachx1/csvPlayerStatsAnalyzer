package com.sachin.stats.service;

import com.sachin.stats.model.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;

public class StatsService {
    private final List<Player> players;

    public StatsService(List<Player> players) {
        this.players = players;
    }

    public List<String> teams() {
        return players.stream().map(Player::team).distinct().sorted().toList(); // Maps each player to a team, gets rid
                                                                                // of duplicates and sorts
                                                                                // alphabetically and creates a list
    }

    // Gets the max goals from the record
    public int maxGoals() {
        return players.stream().mapToInt(Player::goals).max().orElse(0);
    }

    // Gets the min goals from the record
    public int minGoals() {
        return players.stream().mapToInt(Player::goals).min().orElse(0);
    }

    // Spits out the players on a given team inputted by the user
    public List<Player> playersOnTeam(String team) {
        return players.stream().filter(p -> p.team().equalsIgnoreCase(team)).toList();
    }

    // Spits out the top scorers
    public List<Player> topScorers() {
        // List<Integer> goals = new ArrayList<>();
        var topScorer = players.stream().filter(g -> g.goals() >= 10).sorted(Comparator.comparingInt(Player::goals))
                .toList();
        return topScorer;
    }

    // Spits out the players who have scored the minimum amount of goals inputted by
    // the user
    public List<Player> playersWithAtLeast(int minGoals) {

        var minAmtGoals = players.stream().filter(g -> g.goals() >= minGoals)
                .sorted(Comparator.comparingInt(Player::goals).reversed()).toList();
        return minAmtGoals;
    }

}
