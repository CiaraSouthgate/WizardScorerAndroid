package com.ciarasouthgate.wizardscorekeeper;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;

public abstract class BidsTricksActivity extends InGameActivity {
    Player dealer;

    int dealerInt;
    int numTricks;
    int currentRound;
    int totalRounds;

    TextView total;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBoxListeners();
    }

    abstract void setBoxListeners();

    void getGameInfo() {
        currentRound = game.getCurrentRound();
        totalRounds = game.getNumRounds();

        numTricks = currentRound;
        Integer maxTricks = game.getMaxTricks(); // will be null unless game is in 1-X-1 mode
        if (maxTricks != null && currentRound > maxTricks) {
            numTricks = maxTricks - (currentRound - maxTricks);
        }

        dealerInt = (currentRound - 1) % players.length;
        dealer = players[dealerInt];

    }

    void setTitles() {
        TextView roundTitle = findViewById(R.id.roundTitle);
        roundTitle.setText(getString(R.string.round_title, currentRound, totalRounds));

        TextView dealerName = findViewById(R.id.dealerName);
        dealerName.setText(dealer.getName());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        newGameDialog(getString(R.string.leave_game_title)).show();
    }

}
