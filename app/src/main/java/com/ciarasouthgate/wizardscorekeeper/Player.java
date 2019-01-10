package com.ciarasouthgate.wizardscorekeeper;

import android.os.Parcel;
import android.os.Parcelable;

public class Player implements Comparable<Player>, Parcelable {
    private final String name;
    private int score;
    private int bid;
    private int actual;

    public Player(String setName) {
        name = setName;
        score = 0;
    }

    public void setBid(int bid) {
        this.bid = bid;
    }

    public void setActual(int actual) {
        this.actual = actual;
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

    public int getActual() {
        return actual;
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

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(score);
        dest.writeInt(bid);
        dest.writeInt(actual);
    }

    private Player(Parcel in) {
        name = in.readString();
        score = in.readInt();
        bid = in.readInt();
        actual = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<Player> CREATOR
            = new Parcelable.Creator<Player>() {

        @Override
        public Player createFromParcel(Parcel in) {
            return new Player(in);
        }

        // We just need to copy this and change the type to match our class.
        @Override
        public Player[] newArray(int size) {
            return new Player[size];
        }
    };
}
