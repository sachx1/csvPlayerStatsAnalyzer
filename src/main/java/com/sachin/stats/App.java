package com.sachin.stats;

import com.sachin.stats.model.Position;
import com.sachin.stats.model.Player;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {

        var neymar = new Player("Neymar JR", "Barcelona", Position.FW, 23, 35, 2900, 14, 11);
        var coutinho = new Player("Coutinho", "Liverpool", Position.MF, 24, 25, 2600, 10, 9);
        var thiagoSilva = new Player("Thiago Silva", "AC Milan", Position.DF, 29, 30, 3500, 1, 1);
        System.out.println(neymar + " " + coutinho + " " + thiagoSilva.goalsPer90());
        System.out.println("Neymar goals per 90 is: " + neymar.goalsPer90());

    }
}
