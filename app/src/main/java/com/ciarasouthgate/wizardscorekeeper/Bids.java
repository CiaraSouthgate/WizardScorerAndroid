package com.ciarasouthgate.wizardscorekeeper;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.TextView;

import static com.ciarasouthgate.wizardscorekeeper.Constants.GAME_ID;

public class Bids extends BidsTricksActivity {
    private EditText[] bids;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    void setLayout() {
        setContentView(R.layout.activity_bids);
    }

    void getViews() {
        appBar = findViewById(R.id.appBar);
        table = findViewById(R.id.bidsTable);
        total = findViewById(R.id.bidTotal);
    }

    void setArrays() {
        playerNames = new TextView[players.length];
        scores = new TextView[players.length];
        bids = new EditText[players.length];
        rows = new TableRow[6];

        for (int i = 1; i < table.getChildCount() - 1; i++) {
            View view = table.getChildAt(i);
            rows[i - 1] = (TableRow) view;
        }

        for (int i = 0; i < players.length; i++) {
            View player = rows[i].getChildAt(0);
            View score = rows[i].getChildAt(1);
            View bid = rows[i].getChildAt(2);
            playerNames[i] = (TextView) player;
            scores[i] = (TextView) score;
            bids[i] = (EditText) bid;
        }
    }

    @SuppressLint("SetTextI18n")
    void setPlayers() {
        for (int i = 1; i <= players.length; i++) {
            Player current = players[(dealerInt + i) % players.length];
            playerNames[i - 1].setText(current.getName());
            scores[i - 1].setText(Integer.toString(current.getScore()));
        }
    }

    void setBoxListeners() {
        for (EditText e : bids) {
            e.setOnFocusChangeListener((v, hasFocus) -> updateTotals());
        }
    }

    @SuppressLint("SetTextI18n")
    void updateTotals() {
        int sum = 0;
        for (EditText b : bids) {
            String bidString = b.getText().toString();
            if (!bidString.equals("")) {
                int bid = Integer.parseInt(bidString);
                sum += bid;
            }
        }
        total.setText(Integer.toString(sum));
    }

    public void onContinuePressed(View v) {
        clearFocus();
        for (int i = 1; i <= players.length; i++) {
            Player current = players[(dealerInt + i) % players.length];
            String bidString = bids[i - 1].getText().toString();
            if (bidString.isEmpty()) {
                displayError(getString(R.string.bid_error));
                return;
            }
            int bid = Integer.parseInt(bidString);
            if (bid > numTricks) {
                displayError(getString(R.string.bid_over_max));
                return;
            }
            current.startNewRound(bid);
        }
        toTricks();
    }

    public void toTricks() {
        saveGame(game);
        Intent intent = new Intent(this, Tricks.class);
        intent.putExtra(GAME_ID, gameId);
        startActivity(intent);
    }
}
