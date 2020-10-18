package com.ciarasouthgate.wizardscorekeeper;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import static com.ciarasouthgate.wizardscorekeeper.Constants.GAME_ID;

public class Bids extends BidsTricksActivity {
    private TableRow[] rows;
    private TextView[] playerNames;
    private TextView[] playerScores;
    private EditText[] bids;

    private TableLayout bidTable;
    private TextView bidTotal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bids);

        bidTable = findViewById(R.id.bidsTable);
        bidTotal = findViewById(R.id.bidTotal);

        playerNames = new TextView[players.length];
        playerScores = new TextView[players.length];
        bids = new EditText[players.length];

        rows = new TableRow[6];

        setTitles();
        setArrays();
        setPlayers();
        showRows();
        setBoxListeners();
    }

    void setArrays() {
        for (int i = 1; i < bidTable.getChildCount() - 1; i++) {
            View view = bidTable.getChildAt(i);
            rows[i - 1] = (TableRow) view;
        }

        for (int i = 0; i < players.length; i++) {
            View player = rows[i].getChildAt(0);
            View score = rows[i].getChildAt(1);
            View bid = rows[i].getChildAt(2);
            playerNames[i] = (TextView) player;
            playerScores[i] = (TextView) score;
            bids[i] = (EditText) bid;
        }
    }

    @SuppressLint("SetTextI18n")
    void setPlayers() {
        for (int i = 1; i <= players.length; i++) {
            Player current = players[(dealerInt + i) % players.length];
            playerNames[i - 1].setText(current.getName());
            playerScores[i - 1].setText(Integer.toString(current.getScore()));
        }
    }

    private void showRows() {
        switch (players.length) {
            case 6:
                rows[5].setVisibility(View.VISIBLE);
            case 5:
                rows[4].setVisibility(View.VISIBLE);
            case 4:
                rows[3].setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
    }

    private void setBoxListeners() {
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
        bidTotal.setText(Integer.toString(sum));
    }

    public void continueButton(View v) {
        clearFocus();
        for (EditText current : bids) {
            if (current.getText().toString().isEmpty()) {
                Toast.makeText(this, R.string.bid_error, Toast.LENGTH_LONG).show();
                return;
            }
        }

        getBids();
        toTricks();
    }

    public void getBids() {
        for (int i = 1; i <= players.length; i++) {
            Player current = players[(dealerInt + i) % players.length];
            String bidString = bids[i - 1].getText().toString();
            int bid = Integer.parseInt(bidString);
            current.startNewRound(bid);
        }
    }

    public void toTricks() {
        saveGame(game);
        Intent intent = new Intent(this, Tricks.class);
        intent.putExtra(GAME_ID, gameId);
        startActivity(intent);
    }
}
