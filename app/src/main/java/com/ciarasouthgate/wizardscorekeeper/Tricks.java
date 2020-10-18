package com.ciarasouthgate.wizardscorekeeper;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import static com.ciarasouthgate.wizardscorekeeper.Constants.GAME_ID;

public class Tricks extends BidsTricksActivity {
    private TableRow[] rows;
    private TextView[] names;
    private TextView[] scores;
    private TextView[] bids;
    private EditText[] tricks;

    private TableLayout trickTable;
    private TextView trickTotal;
    private Button nextRound;

    private int trickSum;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tricks);

        trickTable = findViewById(R.id.tricksTable);
        trickTotal = findViewById(R.id.trickTotal);
        nextRound = findViewById(R.id.continueButton);

        names = new TextView[players.length];
        scores = new TextView[players.length];
        bids = new TextView[players.length];
        tricks = new EditText[players.length];

        rows = new TableRow[6];

        setTitles();
        setArrays();
        setPlayers();
        showRows();
        setBoxListeners();
    }

    void setArrays() {
        for (int i = 1; i < trickTable.getChildCount() - 1; i++) {
            View view = trickTable.getChildAt(i);
            rows[i - 1] = (TableRow) view;
        }

        for (int i = 0; i < players.length; i++) {
            View player = rows[i].getChildAt(0);
            View score = rows[i].getChildAt(1);
            View bid = rows[i].getChildAt(2);
            View trick = rows[i].getChildAt(3);
            names[i] = (TextView) player;
            scores[i] = (TextView) score;
            bids[i] = (TextView) bid;
            tricks[i] = (EditText) trick;
        }
    }

    @SuppressLint("SetTextI18n")
    void setPlayers() {
        for (int i = 1; i <= players.length; i++) {
            Player current = players[(dealerInt + i) % players.length];
            names[i - 1].setText(current.getName());
            scores[i - 1].setText(Integer.toString(current.getScore()));
            bids[i - 1].setText((Integer.toString(current.getLatestBid())));
            tricks[i - 1].getText().clear();
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
        if (currentRound == totalRounds) {
            nextRound.setText("Final Scores");
        }
    }

    private void setBoxListeners() {
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
        trickTotal.setText(Integer.toString(trickSum));
    }

    public void nextRoundButton(View v) {
        clearFocus();
        for (EditText current : tricks) {
            if (current.getText().toString().isEmpty()) {
                Toast.makeText(this, R.string.trickError, Toast.LENGTH_LONG).show();
                return;
            }
        }

        if (!(trickSum == numTricks)) {
            Toast.makeText(this, R.string.invalidQuantity, Toast.LENGTH_LONG).show();
            return;
        }

        getTricks();
        toNext();
    }

    public void getTricks() {
        for (int i = 1; i <= players.length; i++) {
            Player current = players[(dealerInt + i) % players.length];
            String trickString = tricks[i - 1].getText().toString();
            int trick = Integer.parseInt(trickString);
            current.endRound(trick);
        }
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
