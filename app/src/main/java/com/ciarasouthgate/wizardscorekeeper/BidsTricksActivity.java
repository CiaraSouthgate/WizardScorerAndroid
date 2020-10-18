package com.ciarasouthgate.wizardscorekeeper;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import static com.ciarasouthgate.wizardscorekeeper.Constants.GAME_ID;

public abstract class BidsTricksActivity extends GameActivity {
    String gameId;
    Game game;
    Player[] players;
    Player dealer;
    int dealerInt;
    int numTricks;
    int currentRound;
    int totalRounds;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        gameId = getIntent().getStringExtra(GAME_ID);
        game = readGame(gameId);

        players = game.getPlayers();
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
        String roundString = "Round " + currentRound + " of " + totalRounds;
        roundTitle.setText(roundString);

        TextView dealerName = findViewById(R.id.dealerName);
        dealerName.setText(dealer.getName());
    }

    abstract void setArrays();

    abstract void setPlayers();

    abstract void updateTotals();

    AlertDialog.Builder backButtonHandler() {
        return new AlertDialog.Builder(getApplicationContext())
                .setTitle(getString(R.string.leave_game_title))
                .setMessage(getString(R.string.save_game_dialog))
                .setPositiveButton(getString(R.string.save), (dialog, which) -> {
                    saveGame(game);
                    toGameSetup();
                })
                .setNegativeButton(getString(R.string.discard), (dialog, which) -> {
                    savedGames.edit()
                            .remove(game.getId())
                            .apply();
                    toGameSetup();
                })
                .setNeutralButton(getString(R.string.cancel),
                        (dialog, which) -> dialog.cancel());
    }

    void toGameSetup() {
        Intent intent = new Intent(getApplicationContext(), GameSetup.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        backButtonHandler().show();
    }

}
