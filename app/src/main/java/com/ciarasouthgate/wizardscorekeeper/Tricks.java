package com.ciarasouthgate.wizardscorekeeper;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import static com.google.android.material.snackbar.Snackbar.LENGTH_LONG;

public class Tricks extends AppCompatActivity {
    private Game game;
    private Player[] players;
    private Player dealer;
    private int dealerInt;
    private int currentRound;
    private int totalRounds;
    private int trickSum;

    private TableRow[] rows;
    private TextView[] playerNames;
    private TextView[] playerScores;
    private TextView[] bids;
    private EditText[] tricks;

    private TextView roundTitle;
    private TextView dealerName;
    private TableLayout trickTable;
    private TextView trickTotal;
    private Button nextRound;

    CoordinatorLayout snackbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tricks);

        game = getIntent().getParcelableExtra("game");
        players = game.getPlayers();
        currentRound = game.getCurrent();
        totalRounds = game.getTotalRounds();
        dealerInt = (currentRound - 1) % players.length;
        dealer = players[dealerInt];

        roundTitle = findViewById(R.id.roundTitle);
        dealerName = findViewById(R.id.dealerName);
        trickTable = findViewById(R.id.tricksTable);
        trickTotal = findViewById(R.id.trickTotal);
        nextRound = findViewById(R.id.nextRoundButton);

        playerNames = new TextView[players.length];
        playerScores = new TextView[players.length];
        bids = new TextView[players.length];
        tricks = new EditText[players.length];

        snackbar = findViewById(R.id.error);

        rows = new TableRow[6];

        String roundString = "Round " + currentRound + " of " + totalRounds;
        roundTitle.setText(roundString);

        setArrays();
        setPlayers();
        showRows();
        setBoxListeners();
    }

    private void setArrays() {
        for (int i = 1; i < trickTable.getChildCount() - 1; i++) {
            View view = trickTable.getChildAt(i);
            rows[i - 1] = (TableRow) view;
        }

        for (int i = 0; i < players.length; i++) {
            View player = rows[i].getChildAt(0);
            View score = rows[i].getChildAt(1);
            View bid = rows[i].getChildAt(2);
            View trick = rows[i].getChildAt(3);
            playerNames[i] = (TextView) player;
            playerScores[i] = (TextView) score;
            bids[i] = (TextView) bid;
            tricks[i] = (EditText) trick;
        }
    }

    private void setPlayers() {
        dealerName.setText(dealer.getName());
        for (int i = 1; i <= players.length; i++) {
            Player current = players[(dealerInt + i) % players.length];
            playerNames[i - 1].setText(current.getName());
            playerScores[i - 1].setText(Integer.toString(current.getScore()));
            bids[i - 1].setText((Integer.toString(current.getBid())));
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
            e.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    updateTotals();
                }
            });

            e.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_DONE) {
                        updateTotals();
                        return true;
                    }
                    return false;
                }
            });
        }
    }

    private void updateTotals() {
        trickSum = 0;
        for (EditText t : tricks) {
            String bidString = t.getText().toString();
            if (!bidString.equals("")) {
                int bid = Integer.parseInt(bidString);
                trickSum += bid;
            }
        }
        trickTotal.setText(Integer.toString(trickSum));
    }

    public void nextRoundButton(View v) {
        boolean empty = false;
        for (int i = 0; i < tricks.length; i++) {
            EditText current = tricks[i];
            if (current.getText().toString().equals("")) {
                empty = true;
                Snackbar.make(snackbar, R.string.trickError, LENGTH_LONG).show();
                break;
            }
        }

        if (!empty) {
            updateTotals();
            if (!(trickSum == currentRound)) {
                Snackbar.make(snackbar, R.string.invalidQuantity, LENGTH_LONG).show();
            } else {
                getTricks();
                updateScores();
                game.nextRound();
                toNext();
            }
        }
    }

    private void getTricks() {
        for (int i = 1; i <= players.length; i++) {
            Player current = players[(dealerInt + i) % players.length];
            String trickString = tricks[i - 1].getText().toString();
            int trick = Integer.parseInt(trickString);
            current.setTricks(trick);
        }
    }

    public void updateScores() {
        for (Player p : players) {
            p.updateScore();
        }
    }

    public void toNext() {
        if (currentRound < totalRounds) {
            Intent intent = new Intent(this, Bids.class);
            intent.putExtra("game", game);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, Final.class);
            intent.putExtra("game", game);
            startActivity(intent);
        }
    }

    public void editBids(View v) {
        Intent intent = new Intent(this, Bids.class);
        intent.putExtra("game", game);
        startActivity(intent);
    }
}
