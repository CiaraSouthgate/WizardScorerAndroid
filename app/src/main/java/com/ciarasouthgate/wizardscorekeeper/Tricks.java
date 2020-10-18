package com.ciarasouthgate.wizardscorekeeper;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.TextView;

import static com.ciarasouthgate.wizardscorekeeper.Constants.GAME_ID;

public class Tricks extends BidsTricksActivity {
    private TextView[] bids;
    private EditText[] tricks;

    private Button nextRound;
    private int trickSum;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (currentRound == totalRounds) {
            nextRound.setText(R.string.final_scores);
        }
    }

    void setLayout() {
        setContentView(R.layout.activity_tricks);
    }

    void getViews() {
        appBar = findViewById(R.id.appBar);

        table = findViewById(R.id.tricksTable);
        total = findViewById(R.id.trickTotal);
        nextRound = findViewById(R.id.continueButton);

        playerNames = new TextView[players.length];
        scores = new TextView[players.length];
        bids = new TextView[players.length];
        tricks = new EditText[players.length];

        rows = new TableRow[6];
    }

    void setArrays() {
        for (int i = 1; i < table.getChildCount() - 1; i++) {
            View view = table.getChildAt(i);
            rows[i - 1] = (TableRow) view;
        }

        for (int i = 0; i < players.length; i++) {
            View player = rows[i].getChildAt(0);
            View score = rows[i].getChildAt(1);
            View bid = rows[i].getChildAt(2);
            View trick = rows[i].getChildAt(3);
            playerNames[i] = (TextView) player;
            scores[i] = (TextView) score;
            bids[i] = (TextView) bid;
            tricks[i] = (EditText) trick;
        }
    }

    @SuppressLint("SetTextI18n")
    void setPlayers() {
        for (int i = 1; i <= players.length; i++) {
            Player current = players[(dealerInt + i) % players.length];
            playerNames[i - 1].setText(current.getName());
            scores[i - 1].setText(Integer.toString(current.getScore()));
            bids[i - 1].setText((Integer.toString(current.getLatestBid())));
            tricks[i - 1].getText().clear();
        }
    }

    void setBoxListeners() {
        for (EditText e : tricks) {
            e.setOnFocusChangeListener((v, hasFocus) -> updateTotals());
        }
    }

    @SuppressLint("SetTextI18n")
    void updateTotals() {
        trickSum = 0;
        for (EditText t : tricks) {
            String trickString = t.getText().toString();
            if (!trickString.isEmpty()) {
                int bid = Integer.parseInt(trickString);
                trickSum += bid;
            }
        }
        total.setText(Integer.toString(trickSum));
    }

    public void nextRoundButton(View v) {
        clearFocus();

        if (!(trickSum == numTricks)) {
            displayError(getString(R.string.invalidQuantity));
            return;
        }

        for (int i = 1; i <= players.length; i++) {
            Player current = players[(dealerInt + i) % players.length];
            String trickString = tricks[i - 1].getText().toString();
            if (trickString.isEmpty()) {
                displayError(getString(R.string.trickError));
                return;
            }
            int trick = Integer.parseInt(trickString);
            current.endRound(trick);
        }

        toNext();
    }

    public void toNext() {
        game.increaseRound();
        saveGame(game);
        if (currentRound < totalRounds) {
            toBids(null);
        } else {
            toFinal();
        }
    }

    public void toBids(View v) {
        Intent intent = new Intent(this, Bids.class);
        intent.putExtra(GAME_ID, gameId);
        startActivity(intent);
    }

    public void toFinal() {
        Intent intent = new Intent(this, Final.class);
        intent.putExtra(GAME_ID, gameId);
        startActivity(intent);
    }
}
