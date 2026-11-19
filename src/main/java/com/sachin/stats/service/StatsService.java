package com.sachin.stats.service;

import com.sachin.stats.model.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

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

    // A list of names that is returned to the app
    public List<String> names() {
        return players.stream().map(Player::name).distinct().sorted().toList();
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
    public Map<String, Integer> topScorers() {
        Map<String, Integer> scorerMap = players.stream()
                .collect(Collectors.groupingBy(Player::name, TreeMap::new, Collectors.summingInt(Player::goals)));
        return scorerMap;
    }

    // Spits out the players who have scored the minimum amount of goals inputted by
    // the user
    public List<Player> playersWithAtLeast(int minGoals) {

        var minAmtGoals = players.stream().filter(g -> g.goals() >= minGoals)
                .sorted(Comparator.comparingInt(Player::goals).reversed()).toList();
        return minAmtGoals;
    }

    public Map<String, Integer> totalGoalsPerTeam(String team) {
        // Command to summarize each group
        // Map<String, List<Player>> byTeam =
        // players.stream().collect(Collectors.groupingBy(Player::team));
        Map<String, Integer> byTeam = players.stream()
                .collect(Collectors.groupingBy(Player::team, Collectors.summingInt(Player::goals)));
        // System.out.println(byTeam);
        return byTeam;
    }

    // Returns a list that is filtered by the player name, so we can search for a
    // player
    public List<Player> playerByName(String playerName) {
        return players.stream().filter(p -> p.name().toLowerCase().contains(playerName.toLowerCase())).toList();
    }

}
