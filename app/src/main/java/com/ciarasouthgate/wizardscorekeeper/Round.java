package com.ciarasouthgate.wizardscorekeeper;

public class Round {
    private final int bid;
    private Integer taken;
    private int scoreDiff;

    public Round(int bid) {
        this.bid = bid;
        this.taken = null;
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

    public boolean isFinished() {
        return this.taken != null;
    }

    public int getScoreDiff() {
        return this.scoreDiff;
    }
}
