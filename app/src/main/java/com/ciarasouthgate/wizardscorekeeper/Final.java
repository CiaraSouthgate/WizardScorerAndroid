package com.ciarasouthgate.wizardscorekeeper;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TableRow;
import android.widget.TextView;

public class Final extends InGameActivity {
    private Player winner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    void setLayout() {
        setContentView(R.layout.activity_final);
    }

    void getViews() {
        appBar = findViewById(R.id.appBar);
        table = findViewById(R.id.scoresTable);
    }

    void getGameInfo() {
        winner = game.getLeading();
    }

    void setTitles() {
        TextView winnerName = findViewById(R.id.winnerName);
        winnerName.setText(winner.getName());
    }

    void setArrays() {
        playerNames = new TextView[players.length];
        scores = new TextView[players.length];

        rows = new TableRow[6];
        for (int i = 1; i < table.getChildCount(); i++) {
            View view = table.getChildAt(i);
            rows[i - 1] = (TableRow) view;
        }

        for (int i = 0; i < players.length; i++) {
            View player = rows[i].getChildAt(0);
            View score = rows[i].getChildAt(1);
            playerNames[i] = (TextView) player;
            scores[i] = (TextView) score;
        }
    }

    @SuppressLint("SetTextI18n")
    void setPlayers() {
        for (int i = 0; i < players.length; i++) {
            Player current = players[i];
            playerNames[i].setText(current.getName());
            scores[i].setText(Integer.toString(current.getScore()));
        }
    }

    public void newGame(View v) {
        Intent intent = new Intent(this, GameSetup.class);
        startActivity(intent);
    }
}
