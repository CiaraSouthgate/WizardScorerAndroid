package com.ciarasouthgate.wizardscorekeeper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ScorePad extends AppCompatActivity {
    Game game;
    TextView player1;
    TextView player2;
    TextView player3;
    TextView player4;
    TextView player5;
    TextView player6;

    Player[] players;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        game = getIntent().getParcelableExtra("game");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_pad);

        players = game.getPlayers();

        player1 = findViewById(R.id.player1);
        player2 = findViewById(R.id.player2);
        player3 = findViewById(R.id.player3);
        player4 = findViewById(R.id.player4);
        player5 = findViewById(R.id.player5);
        player6 = findViewById(R.id.player6);

        player1.setText(players[0].getName());
        player2.setText(players[1].getName());
        player3.setText(players[2].getName());
        player4.setText(players[3].getName());
        player5.setText(players[4].getName());
        player6.setText(players[5].getName());
    }
}
