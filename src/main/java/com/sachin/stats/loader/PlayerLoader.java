package com.sachin.stats.loader;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.sachin.stats.model.Player;
import com.sachin.stats.model.Position;

public class PlayerLoader {
    /**
     * Loads players from a CSV file. The first line is treated as a header and
     * skipped. Rows that don't have 8 columns or can't be parsed are skipped
     * with a warning that includes their line number.
     *
     * @param path The path to the CSV file.
     * @return The players from every valid row.
     * @throws IOException If the file doesn't exist or can't be read.
     */
    public List<Player> load(Path path) throws IOException {
        List<List<String>> rows = readRows(path);
        return parsePlayers(rows);
    }

    private List<List<String>> readRows(Path filePath) throws IOException {
        List<List<String>> records = new ArrayList<>();

        try (BufferedReader br = Files.newBufferedReader(filePath)) {
            br.readLine(); // Skips header line
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",", -1);
                records.add(Arrays.asList(values));
            }
        }

        return records;
    }

    private List<Player> parsePlayers(List<List<String>> records) {
        List<Player> players = new ArrayList<>();
        // Loop through the array list, and parse the values to a temp variable, then
        // add to the array list
        for (int i = 0; i < records.size(); i++) {
            var initial = records.get(i);
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
                System.out.println("Skipping line: " + (i + 2) + " " + e.getMessage());
            }

        }
        return players;
    }
}
