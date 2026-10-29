package com.sachin.stats.loader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PlayerLoader {
    /**
     * Reads a CSV file and loads its contents into a 2D list.
     * 
     * @param filePath The path to the CSV file.
     * @return A list of lists representing rows and columns.
     * @throws IOException If an I/O error occurs opening or reading the file.
     */
    public List<List<String>> loadCSV(String filePath) throws IOException {
        List<List<String>> records = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            br.readLine(); // Skips header line
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                records.add(Arrays.asList(values));
            }
        }

        return records;
    }
}
