package com.ciarasouthgate.wizardscorekeeper;

import android.os.Parcel;
import android.os.Parcelable;

public class Game implements Parcelable {
    private Player[] players;
    private Round[] rounds;
    private int current = 1;
    private int roundsNo;


    public Game(Player[] players) {
        this.players = players;
        switch (players.length) {
            case 3:
                roundsNo = 20;
                break;
            case 4:
                roundsNo = 15;
                break;
            case 5:
                roundsNo = 12;
                break;
            case 6:
                roundsNo = 10;
                break;
        }
        rounds = new Round[roundsNo];

        for (int i = 0; i < roundsNo; i++) {
            rounds[i] = new Round(players);
        }
    }

    public Player[] getPlayers() {
        return players;
    }

    public int getCurrent() {
        return current;
    }

    public int getRoundsNo() {
        return roundsNo;
    }

    public void nextRound() {
        current++;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedArray(players, flags);
        dest.writeTypedArray(rounds, flags);
        dest.writeInt(roundsNo);
    }

    private Game(Parcel in) {
        players = in.createTypedArray(Player.CREATOR);
        rounds = in.createTypedArray(Round.CREATOR);
        roundsNo = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<Game> CREATOR
            = new Parcelable.Creator<Game>() {

        @Override
        public Game createFromParcel(Parcel in) {
            return new Game(in);
        }

        @Override
        public Game[] newArray(int size) {
            return new Game[size];
        }
    };
}
