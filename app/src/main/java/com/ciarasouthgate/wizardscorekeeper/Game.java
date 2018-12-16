package com.ciarasouthgate.wizardscorekeeper;

import java.util.Scanner;

public class Game {
    private Player[] players;
    private int rounds;
    private Scanner scan;

    public Game(Player[] players) {
        scan = new Scanner(System.in);
        this.players = players;
        switch (players.length) {
            case 3:
                rounds = 20;
                break;
            case 4:
                rounds = 15;
                break;
            case 5:
                rounds = 12;
                break;
            case 6:
                rounds = 10;
                break;
        }
    }
}
