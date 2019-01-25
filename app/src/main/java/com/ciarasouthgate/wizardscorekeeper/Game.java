package com.ciarasouthgate.wizardscorekeeper;

import android.os.Parcel;
import android.os.Parcelable;

public class Game implements Parcelable {
    private Player[] players;
    private int current;
    private int totalRounds;
    private boolean dealerAllowedEqual;

    public Game(Player[] players) {
        current = 1;
        dealerAllowedEqual = true;
        this.players = players;
        switch (players.length) {
            case 3:
                totalRounds = 20;
                break;
            case 4:
                totalRounds = 15;
                break;
            case 5:
                totalRounds = 12;
                break;
            case 6:
                totalRounds = 10;
                break;
        }
    }

    public Player[] getPlayers() {
        return players;
    }

    public int getCurrent() {
        return current;
    }

    public int getTotalRounds() {
        return totalRounds;
    }

    public void nextRound() {
        current++;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedArray(players, flags);
        dest.writeInt(current);
        dest.writeInt(totalRounds);
        dest.writeInt(dealerAllowedEqual ? 1 : 0);
    }

    private Game(Parcel in) {
        players = in.createTypedArray(Player.CREATOR);
        current = in.readInt();
        totalRounds = in.readInt();
        dealerAllowedEqual = (in.readInt() == 0) ? false : true;
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
