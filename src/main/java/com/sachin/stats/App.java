package com.sachin.stats;

import com.sachin.stats.model.Position;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import com.sachin.stats.model.Player;
import com.sachin.stats.loader.PlayerLoader;
import java.util.ArrayList;
import java.util.List;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) throws IOException {

        List<Player> players = new ArrayList<>(); // List using type Player, used to parse the players from the CSV
        Path filePath = Path.of("src/main/resources/players.csv"); // players.csv path
        String fileString = filePath.toString(); // Turns the path into a string for the PlayerLoader class to use
        var csv = new PlayerLoader(); // Call the class to load the csv
        List<List<String>> load; // Declare load with type List, no value yet
        try {
            load = csv.loadCSV(fileString); // Assign load to the function inside the PlayerLoader Class
        } catch (FileNotFoundException e) {
            System.out.println(e); // Catches the exception and ends the program
            return;
        }

        // Loop through the array list, and parse the values to a temp variable, then
        // add to the array list
        for (int i = 0; i < load.size(); i++) {
            var initial = load.get(i);
            if (initial.size() != 8) {
                // Had to put i+2 since we skip a line after loading the CSV
                System.out.println("Skipping line: " + (i + 2) + " Expected 8, found " + initial.size());
                continue;
            }
            try {
                String name = initial.get(0);
                String team = initial.get(1);
                Position position = Position.valueOf(initial.get(2));
                int age = Integer.parseInt(initial.get(3));
                int appearances = Integer.parseInt(initial.get(4));
                int minutes = Integer.parseInt(initial.get(5));
                int goals = Integer.parseInt(initial.get(6));
                int assists = Integer.parseInt(initial.get(7));
                players.add(new Player(name, team, position, age, appearances, minutes, goals, assists));
            } catch (IllegalArgumentException e) {
                System.out.println("Skipping line: " + (i + 2) + " " + e);
                continue;
            }

        }

        System.out.println(players.size());
    }
}
