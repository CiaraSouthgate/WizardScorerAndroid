package com.ciarasouthgate.wizardscorekeeper;

import android.os.Parcel;
import android.os.Parcelable;

public class Round implements Parcelable {
    Player[] players;

    public Round(Player[] players) {
        this.players = players;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedArray(players, flags);
    }

    private Round(Parcel in) {
        players = in.createTypedArray(Player.CREATOR);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<Round> CREATOR
            = new Parcelable.Creator<Round>() {

        @Override
        public Round createFromParcel(Parcel in) {
            return new Round(in);
        }

        @Override
        public Round[] newArray(int size) {
            return new Round[size];
        }
    };
}
