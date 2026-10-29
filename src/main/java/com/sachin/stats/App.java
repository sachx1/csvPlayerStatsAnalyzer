package com.sachin.stats;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import com.sachin.stats.loader.PlayerLoader;
import com.sachin.stats.model.Player;

public class App {
    public static void main(String[] args) {

        Path filePath = Path.of("src/main/resources/players.csv"); // players.csv path
        var loader = new PlayerLoader();
        List<Player> players;
        try {
            players = loader.load(filePath);
        } catch (IOException e) {
            System.out.println("Could not read file: " + e.getMessage());
            return;
        }

        System.out.println("Loaded " + players.size() + " players");
    }
}
