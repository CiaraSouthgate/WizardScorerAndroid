package com.ciarasouthgate.wizardscorekeeper;

public class Player implements Comparable<Player>{
    private final String name;
    private int score;
    private int bid;
    private int taken;

    public Player(String setName) {
        name = setName;
        score = 0;
    }

    public void setBid(int bid) {
        this.bid = bid;
    }

    public void setTaken(int taken) {
        this.taken = taken;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public int getBid() {
        return bid;
    }

    public int getTaken() {
        return taken;
    }

    public void changeScore(int n) {
        score += n;
    }

    public int compareTo(Player other) {
        if (score > other.getScore()) {
            return -1;
        } else if (score < other.getScore()) {
            return 1;
        }
        return 0;
    }

    public String toString() {
        return name + ": " + score;
    }
}
