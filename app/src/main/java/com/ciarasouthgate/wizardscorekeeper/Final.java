package com.ciarasouthgate.wizardscorekeeper;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.Arrays;

import androidx.appcompat.app.AppCompatActivity;

public class Final extends AppCompatActivity {
    private Game game;
    private Player[] players;
    private Player winner;

    private TableRow[] rows;
    private TextView[] playerNames;
    private TextView[] playerScores;

    private TextView winnerName;
    private TableLayout scoresTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);

        game = getIntent().getParcelableExtra("game");
        players = game.getPlayers();

        Arrays.sort(players);
        winner = players[0];

        winnerName = findViewById(R.id.winnerName);
        scoresTable = findViewById(R.id.scoresTable);

        playerNames = new TextView[players.length];
        playerScores = new TextView[players.length];

        rows = new TableRow[6];

        setArrays();
        setPlayers();
        showRows();
    }

    private void setArrays() {
        for (int i = 1; i < scoresTable.getChildCount(); i++) {
            View view = scoresTable.getChildAt(i);
            rows[i - 1] = (TableRow) view;
        }

        for (int i = 0; i < players.length; i++) {
            View player = rows[i].getChildAt(0);
            View score = rows[i].getChildAt(1);
            playerNames[i] = (TextView) player;
            playerScores[i] = (TextView) score;
        }
    }

    private void setPlayers() {
        winnerName.setText(winner.getName());
        for (int i = 0; i < players.length; i++) {
            Player current = players[i];
            playerNames[i].setText(current.getName());
            playerScores[i].setText(Integer.toString(current.getScore()));
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

    public void newGame(View v) {
        Intent intent = new Intent(this, PlayerSetup.class);
        startActivity(intent);
    }
}
