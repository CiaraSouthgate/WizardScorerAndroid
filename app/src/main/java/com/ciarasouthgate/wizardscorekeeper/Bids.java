package com.ciarasouthgate.wizardscorekeeper;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import static android.support.design.widget.Snackbar.LENGTH_LONG;

public class Bids extends AppCompatActivity {
    private Game game;
    private Player[] players;
    private Player dealer;
    private int dealerInt;
    private int currentRound;
    private int totalRounds;

    private TableRow[] rows;
    private TextView[] playerNames;
    private TextView[] playerScores;
    private EditText[] bids;

    private TextView roundTitle;
    private TextView dealerName;
    private TableLayout bidTable;
    private TextView bidTotal;

    CoordinatorLayout snackbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bids);

        game = getIntent().getParcelableExtra("game");
        players = game.getPlayers();
        currentRound = game.getCurrent();
        totalRounds = game.getTotalRounds();
        dealerInt = (currentRound - 1) % players.length;
        dealer = players[dealerInt];

        roundTitle = findViewById(R.id.roundTitle);
        dealerName = findViewById(R.id.dealerName);
        bidTable = findViewById(R.id.bidsTable);
        bidTotal = findViewById(R.id.bidTotal);

        playerNames = new TextView[players.length];
        playerScores = new TextView[players.length];
        bids = new EditText[players.length];

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

    private void setPlayers() {
        dealerName.setText(dealer.getName());
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
        boolean empty = false;
        for (int i = 0; i < bids.length; i++) {
            EditText current = bids[i];
            if (current.getText().toString().equals("")) {
                empty = true;
                Snackbar.make(snackbar, R.string.bidError, LENGTH_LONG).show();
                break;
            }
        }

        if (!empty) {
            getBids();
            toTricks();
        }
    }

    public void getBids(){
        for (int i = 1; i <= players.length; i++) {
            Player current = players[(dealerInt + i) % players.length];
            String bidString = bids[i - 1].getText().toString();
            int bid = Integer.parseInt(bidString);
            current.setBid(bid);
        }
    }

    public void toTricks(){
        Intent intent = new Intent(this, Tricks.class);
        intent.putExtra("game", game);
        startActivity(intent);
    }

    public void onBackPressed() {
        backButtonHandler();
    }

    public void backButtonHandler() {
        AlertDialog.Builder alert = new AlertDialog.Builder(Bids.this);
        alert.setTitle("End game?");
        alert.setMessage("Do you want to end the current game " +
                "and return to the player setup screen?");
        alert.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        newGame();
                    }
                });
        alert.setNegativeButton("NO",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        alert.show();
    }

    public void newGame() {
        Intent intent = new Intent(this, PlayerSetup.class);
        startActivity(intent);
    }
}
