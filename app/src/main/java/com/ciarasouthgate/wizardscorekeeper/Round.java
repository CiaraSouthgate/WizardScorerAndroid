package com.ciarasouthgate.wizardscorekeeper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Round extends AppCompatActivity {
    Button nextRound;
    TextView round;
    Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        game = getIntent().getParcelableExtra("game");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_round);

        nextRound = findViewById(R.id.nextRound);

        round = findViewById(R.id.round);

        System.out.println(game.getCurrent());

        round.setText(setHeader());
    }

    public void nextRound(View v) {
        game.nextRound();
        round.setText(setHeader());
    }

    private String setHeader() {
        String roundText = getString(R.string.round);
        int current = game.getCurrent();
        String of = getString(R.string.of);
        int rounds = game.getRounds();
        return roundText + " " + current + " " + of + " " + rounds;
    }
}
