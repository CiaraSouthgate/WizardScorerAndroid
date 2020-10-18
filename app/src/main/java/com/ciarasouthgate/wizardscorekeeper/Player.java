package com.ciarasouthgate.wizardscorekeeper;

import java.util.ArrayList;
import java.util.List;

public class Player implements Comparable<Player> {
    private final String name;
    private int score;
    private final List<Round> rounds;

    public Player(String name) {
        this.name = name;
        score = 0;
        rounds = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public List<Round> getRounds() {
        return rounds;
    }

    public void startNewRound(int bid) {
        rounds.add(new Round(bid));
    }

    public int getBid(int roundNo) {
        return rounds.get(roundNo - 1).getBid();
    }

    public int getLatestBid() {
        return rounds.get(rounds.size() - 1).getBid();
    }

    public void endRound(int taken) {
        Round activeRound = rounds.get(rounds.size() - 1);
        activeRound.setTaken(taken);
        score += activeRound.getScoreDiff();
    }

    public void editRound(Round round, int roundNo) {
        rounds.set(roundNo - 1, round);
        score = rounds.stream().mapToInt(Round::getScoreDiff).sum();
    }

    public int compareTo(Player other) {
        return Integer.compare(other.getScore(), score);
    }
}
