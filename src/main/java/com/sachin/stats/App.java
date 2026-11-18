package com.sachin.stats;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import com.sachin.stats.loader.PlayerLoader;
import com.sachin.stats.model.Player;
import com.sachin.stats.service.StatsService;
import java.util.Scanner;
import java.util.Map;

public class App {
    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        Path filePath = Path.of("src/main/resources/players.csv"); // players.csv path
        var loader = new PlayerLoader();
        List<Player> players;
        try {
            players = loader.load(filePath);
        } catch (IOException e) {
            System.out.println("Could not read file: " + e.getMessage());
            in.close();
            return;
        }

        var stats = new StatsService(players);

        boolean running = true;
        while (running) {
            System.out.println("Press 1 to select a team");
            System.out.println("Press 2 to see the top goal scorers");
            System.out.println("Press 3 to find total goals per team");
            System.out.println("Press 4 to players by name");
            System.out.println("Press 7 to end");

            String input = in.nextLine();

            if (input.equals("")) {
                System.out.println("Cannot take null values");

            } else if (input.equals("1")) {
                boolean done = true;
                while (done) {
                    List<String> teams = stats.teams();
                    System.out.println("Enter a team to see how many players are in that team:");
                    System.out.println("Press 0 to go back");
                    String team = in.nextLine();
                    boolean exists = teams.stream().anyMatch(t -> t.equalsIgnoreCase(team));
                    if (team.equals("")) {
                        System.out.println("Cannot take null values");
                    } else if (team.equals("0")) {
                        done = false;
                    } else if (exists == false) {
                        System.out.println("This team does not exist in our records, please type another one in");
                    } else {
                        for (Player p : stats.playersOnTeam(team)) {
                            System.out.println(p.name() + ": " + p.team());
                        }
                    }
                }

            } else if (input.equals("2")) {
                boolean done = true;
                int min = stats.minGoals();
                int max = stats.maxGoals();
                while (done) {
                    System.out.println(
                            "Enter the minimum amount of goals to see players who scored that amount or more, must be greater than 0");
                    System.out.println("Type in Top to see the Top Scorers to see the top 10 scorers");
                    System.out.println("Press 0 to go back");
                    String goals = in.nextLine();
                    if (goals.equals("0")) {
                        done = false;
                    } else if (!goals.equals("Top")) {
                        int goalsNumber = 0;
                        try {
                            goalsNumber = Integer.parseInt(goals);
                            if (goalsNumber < min || goalsNumber > max) {
                                min += 1;
                                System.out.println("Out of range. Enter a number between " + min + " and " + max + ".");
                            } else {
                                for (Player p : stats.playersWithAtLeast(goalsNumber)) {
                                    System.out.println(p);
                                    System.out.println(p.name() + " - Goals: " + p.goals());
                                }
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Not a number or a whole number: " + goals);
                        }
                    } else {
                        for (Player p : stats.topScorers()) {
                            System.out.println(p.name() + " - Goals: " + p.goals());
                        }
                    }
                }

            } else if (input.equals("3")) {
                // System.out.println(stats.totalGoalsPerTeam("Arsenal"));
                boolean done = true;
                while (done) {
                    int goalsByTeam = 0;
                    List<String> teams = stats.teams();
                    Map<String, Integer> byTeam = stats.totalGoalsPerTeam("Arsenal");
                    System.out.println("Type in the team name to see the amount of goals 1 team has scored");
                    System.out.println("Press 0 to go back");
                    String teamName = in.nextLine();
                    boolean exists = teams.stream().anyMatch(t -> t.equalsIgnoreCase(teamName));
                    if (teamName.equals("0")) {
                        done = false;
                    } else if (!exists) {
                        System.out.println(
                                "Invalid team name, please type in a team that is inside the records\n" + teams);
                    } else {
                        for (String team : byTeam.keySet()) {
                            if (teamName.equalsIgnoreCase(team)) {
                                goalsByTeam = byTeam.get(team);
                                System.out.println(
                                        teamName + " has scored a combined amount of " + goalsByTeam + " goals");
                            }
                        }
                    }
                }

            } else if (input.equals("4")) {
                boolean done = true;
                List<String> listNames = stats.names();
                while (done) {
                    System.out.println("Type in a player name to see their record");
                    System.out.println("Press 0 to go back");
                    String name = in.nextLine();
                    // boolean exists = listNames.stream().anyMatch(t -> t.equalsIgnoreCase(name));
                    if (name.equals("0")) {
                        done = false;
                    } else if (name.equals("")) {
                        System.out.println(
                                "Invalid name or name does not exist in records, please type in a valid name from our records\n"
                                        + listNames);
                    } else {
                        List<Player> matches = stats.playerByName(name);
                        if (matches.isEmpty()) {
                            System.out.println("No player with that name, please type in a valid name\n" + listNames);
                        } else {
                            for (Player p : stats.playerByName(name)) {
                                System.out.println("\n" + "Name: " + p.name() + "\n" + "Team: " + p.team() + "\n"
                                        + "Position: "
                                        + p.position() + "\n" + "Age: " + p.age() + "\n" + "Appearances: "
                                        + p.appearances()
                                        + "\n" + "Minutes: " + p.minutes() + "\n" + "Goals: " + p.goals() + "\n"
                                        + "Assists: " + p.assists() + "\n");
                            }
                        }
                    }
                }
            } else if (input.equals("7")) {
                running = false;
            }

            // var arsenal = stats.playersOnTeam("Arsenal");
            // var scorers = stats.topScorers(3);
            // System.out.println(arsenal);
            // System.out.println(scorers);

            // System.out.println("Loaded " + players.size() + " players");
        }

        in.close();
        System.out.println("Program terminated");
    }
}
