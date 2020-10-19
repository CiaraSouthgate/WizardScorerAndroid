package com.ciarasouthgate.wizardscorekeeper;

import java.time.LocalDateTime;
import java.util.Arrays;

import static com.ciarasouthgate.wizardscorekeeper.Constants.SAVE_FORMAT;

public class Game implements Comparable<Game> {
    private final String id;
    private final int numRounds;
    private final boolean cdnRules;
    private final boolean noEven;
    private final Integer maxTricks;
    private final Player[] players;
    private int currentRound;
    private String lastSave;

    public Game(int numRounds, boolean cdnRules, boolean noEven,
                Integer maxTricks, Player[] players) {
        this.id = LocalDateTime.now().format(SAVE_FORMAT);
        this.numRounds = numRounds;
        this.cdnRules = cdnRules;
        this.noEven = noEven;
        this.maxTricks = maxTricks;
        this.players = players;
        this.currentRound = 1;
        this.lastSave = LocalDateTime.now().format(SAVE_FORMAT);
    }

    public String getId() {
        return id;
    }

    public int getNumRounds() {
        return numRounds;
    }

    public String getLastSave() {
        return lastSave;
    }

    public int getCurrentRound() {
        return currentRound;
    }

    public boolean isCdnRule() {
        return cdnRules;
    }

    public boolean isNoEven() {
        return noEven;
    }

    public Integer getMaxTricks() {
        return maxTricks;
    }

    public Player[] getPlayers() {
        return players;
    }

    public void increaseRound() {
        currentRound++;
    }

    public void updateSave() {
        this.lastSave = LocalDateTime.now().format(SAVE_FORMAT);
    }

    public boolean isNewRound() {
        return players[0].isRoundFinished();
    }

    public Player getLeading() {
        Player[] sorted = players.clone();
        Arrays.sort(sorted);
        return sorted[0];
    }

    @Override
    public int compareTo(Game other) {
        return this.lastSave.compareTo(other.lastSave);
    }
}

