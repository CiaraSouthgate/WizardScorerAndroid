package com.ciarasouthgate.wizardscorekeeper;

import android.support.constraint.ConstraintLayout;
import android.support.constraint.Group;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class ScorePad extends AppCompatActivity {
    final int MAX_PLAYERS = 6;

    Game game;
    Player[] players;

    Group namesGroup;
    TextView[] names;

    ConstraintLayout help;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        game = getIntent().getParcelableExtra("game");
        players = game.getPlayers();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scorepad);

        help = findViewById(R.id.help);

        namesGroup = findViewById(R.id.playerNames);

        names = new TextView[]{findViewById(R.id.p1name), findViewById(R.id.p2name),
                findViewById(R.id.p3name), findViewById(R.id.p4name),
                findViewById(R.id.p5name), findViewById(R.id.p6name)};

        setNames();
        clearColumns();
    }

    public void setNames(){}

    public void clearColumns() {}

    public void showHelp(View v) {
        help.setVisibility(View.VISIBLE);
    }

    public void hideHelp(View v) {
        help.setVisibility(View.GONE);
    }
}
