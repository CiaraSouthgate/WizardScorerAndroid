package com.ciarasouthgate.wizardscorekeeper;

public class Round {
    private int bid;
    private int taken;
    private int scoreDiff;

    public Round(int bid) {
        this.bid = bid;
    }

    public int getBid() {
        return this.bid;
    }

    public int getTaken() {
        return this.taken;
    }

    public void setTaken(int tricks) {
        taken = tricks;
        int diff = Math.abs(taken - bid);
        this.scoreDiff = diff == 0 ? bid * 10 + 20 : -(diff * 10);
    }

    public int getScoreDiff() {
        return this.scoreDiff;
    }
}
