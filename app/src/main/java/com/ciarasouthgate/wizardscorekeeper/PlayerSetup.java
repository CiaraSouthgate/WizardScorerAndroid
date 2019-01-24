package com.ciarasouthgate.wizardscorekeeper;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import static android.support.design.widget.Snackbar.LENGTH_LONG;
import static android.support.design.widget.Snackbar.LENGTH_SHORT;

public class PlayerSetup extends AppCompatActivity {
    Button startButton;

    EditText Player1;
    EditText Player2;
    EditText Player3;
    EditText Player4;
    EditText Player5;
    EditText Player6;

    CoordinatorLayout snackbar;

    int playerCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_setup);

        Player1 = findViewById(R.id.Player1);
        Player2 = findViewById(R.id.Player2);
        Player3 = findViewById(R.id.Player3);
        Player4 = findViewById(R.id.Player4);
        Player5 = findViewById(R.id.Player5);
        Player6 = findViewById(R.id.Player6);

        startButton = findViewById(R.id.startButton);

        snackbar = findViewById(R.id.error);

        playerCount = 3;
    }

    public void addPlayer(View v) {
        switch (playerCount) {
            case 3:
                Player4.setVisibility(View.VISIBLE);
                playerCount++;
                break;
            case 4:
                Player5.setVisibility(View.VISIBLE);
                playerCount++;
                break;
            case 5:
                Player6.setVisibility(View.VISIBLE);
                playerCount++;
                break;
            case 6:
                Snackbar.make(snackbar, R.string.max_players, LENGTH_LONG).show();
                break;
            default:
                break;
        }
    }

    public void removePlayer(View v) {
        switch (playerCount) {
            case 3:
                Snackbar.make(snackbar, R.string.min_players, LENGTH_LONG).show();
                break;
            case 4:
                Player4.setVisibility(View.GONE);
                Player4.setText("");
                playerCount--;
                break;
            case 5:
                Player5.setVisibility(View.GONE);
                Player5.setText("");
                playerCount--;
                break;
            case 6:
                Player6.setVisibility(View.GONE);
                Player6.setText("");
                playerCount--;
                break;
            default:
                break;
        }
    }

    public void startButton(View v) {
        Player[] players = new Player[playerCount];
        String[] playerNames = new String[playerCount];
        boolean empty = false;

        switch (playerCount) {
            case 6:
                playerNames[5] = (Player6.getText().toString());
            case 5:
                playerNames[4] = (Player5.getText().toString());
            case 4:
                playerNames[3] = (Player4.getText().toString());
            case 3:
                playerNames[2] = (Player3.getText().toString());
                playerNames[1] = (Player2.getText().toString());
                playerNames[0] = (Player1.getText().toString());
                break;
        }

        for (String s : playerNames) {
            if (s.equals("")) {
                empty = true;
                Snackbar.make(snackbar, R.string.playerNameError, LENGTH_LONG).show();
                break;
            }
        }

        if (!empty) {
            for (int i = 0; i < playerNames.length; i++) {
                players[i] = new Player(playerNames[i]);
                Game game = new Game(players);
                Intent intent = new Intent(this, RoundSetup.class);
                intent.putExtra("game", game);
                startActivity(intent);
            }
        }
    }
}

