package com.ciarasouthgate.wizardscorekeeper;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class PlayerSetup extends AppCompatActivity {
    Button startButton;
    Toolbar appBar;

    EditText Player1;
    EditText Player2;
    EditText Player3;
    EditText Player4;
    EditText Player5;
    EditText Player6;

    int playerCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_setup);

        Player1 = findViewById(R.id.player1name);
        Player2 = findViewById(R.id.player2name);
        Player3 = findViewById(R.id.player3name);
        Player4 = findViewById(R.id.player4name);
        Player5 = findViewById(R.id.player5name);
        Player6 = findViewById(R.id.player6name);

        startButton = findViewById(R.id.startButton);
        appBar = findViewById(R.id.setupAppBar);
        appBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.loadSaved:
                        //TODO
                        break;
                    case R.id.alternateRules:
                        startActivity(new Intent(getApplicationContext(), AlternateRules.class));
                        break;
                    case R.id.contact:
                        sendEmail();
                        break;
                }
                return true;
            }
        });
        playerCount = 3;
    }

    private void sendEmail() {
        String details = "\nVERSION.RELEASE : " + Build.VERSION.RELEASE
                + "\nVERSION.INCREMENTAL : " + Build.VERSION.INCREMENTAL
                + "\nVERSION.SDK.NUMBER : " + Build.VERSION.SDK_INT
                + "\nBRAND : " + Build.BRAND
                + "\nHARDWARE : " + Build.HARDWARE
                + "\nHOST : " + Build.HOST
                + "\nID : " + Build.ID
                + "\nMANUFACTURER : " + Build.MANUFACTURER
                + "\nMODEL : " + Build.MODEL
                + "\nPRODUCT : " + Build.PRODUCT
                + "\nDISPLAY : " + Build.DISPLAY
                + "\nTIME : " + Build.TIME;

        String message = "\n\n" + getString(R.string.feedback_email_content) + "\n\n" + details;

        Intent email = new Intent(Intent.ACTION_SENDTO);
        email.setData(Uri.parse("mailto:"));
        email.putExtra(Intent.EXTRA_EMAIL, new String[]{"ciarasouthgate.dev@gmail.com"});
        email.putExtra(Intent.EXTRA_SUBJECT, "Wizard Scorekeeper Feedback");
        email.putExtra(Intent.EXTRA_TEXT, message);
        if (email.resolveActivity(getPackageManager()) != null) {
            startActivity(email);
        }
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
                Toast.makeText(this, R.string.max_players, Toast.LENGTH_LONG).show();
                break;
            default:
                break;
        }
    }

    public void removePlayer(View v) {
        switch (playerCount) {
            case 3:
                Toast.makeText(this, R.string.min_players, Toast.LENGTH_LONG).show();
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
                Toast.makeText(this, R.string.playerNameError, Toast.LENGTH_LONG).show();
                break;
            }
        }

        if (!empty) {
            for (int i = 0; i < playerNames.length; i++) {
                players[i] = new Player(playerNames[i]);
                Game game = new Game(players);
                Intent intent = new Intent(this, Bids.class);
                intent.putExtra("game", game);
                startActivity(intent);
            }
        }
    }
}

