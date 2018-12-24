package com.ciarasouthgate.wizardscorekeeper;

import android.os.Parcel;
import android.os.Parcelable;

public class Game implements Parcelable {
    private Player[] players;
    private int current = 1;
    private int rounds;

    public Game(Player[] players) {
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

    public Player[] getPlayers() {
        return players;
    }

    public int getCurrent() {
        return current;
    }

    public int getRounds() {
        return rounds;
    }

    public void nextRound() {
        current++;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedArray(players, flags);
        dest.writeInt(rounds);
    }

    private Game(Parcel in) {
        players = in.createTypedArray(Player.CREATOR);
        rounds = in.readInt();
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
