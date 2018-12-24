package com.ciarasouthgate.wizardscorekeeper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class ScorePad extends AppCompatActivity {
    Game game;
    Player[] players;

    TableLayout table;
    TableRow namesRow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        game = getIntent().getParcelableExtra("game");
        players = game.getPlayers();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scorepad);

        table = findViewById(R.id.scorepadTable);

        setNames();
        collapseColumns();
    }

    public void setNames() {
        namesRow = findViewById(R.id.namesRow);
        for (int i = 0; i < players.length; i++) {
            View view = namesRow.getChildAt(i);
            String current = players[i].getName();
            if (view instanceof TextView) {
                TextView name = (TextView) view;
                name.setText(current);
            }
        }
    }

    public void collapseColumns() {
        for (int i = players.length + 1; i <= 6; i++) {
            table.setColumnCollapsed(i, true);
        }
        for (int i = 1; i <= players.length; i++) {
            table.setColumnStretchable(i, true);
        }
    }
}
