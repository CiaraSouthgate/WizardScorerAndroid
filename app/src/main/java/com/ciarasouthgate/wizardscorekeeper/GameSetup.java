package com.ciarasouthgate.wizardscorekeeper;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;

import androidx.appcompat.app.AlertDialog;

import static com.ciarasouthgate.wizardscorekeeper.Constants.CDN_RULE;
import static com.ciarasouthgate.wizardscorekeeper.Constants.GAME_ID;
import static com.ciarasouthgate.wizardscorekeeper.Constants.NO_EVEN;
import static com.ciarasouthgate.wizardscorekeeper.Constants.ONE_TO_X;

public class GameSetup extends AppActivity {
    private EditText Player1;
    private EditText Player2;
    private EditText Player3;
    private EditText Player4;
    private EditText Player5;
    private EditText Player6;

    private int playerCount;

    static int getRoundCount(int numPlayers) {
        switch (numPlayers) {
            case 3:
                return 20;
            case 4:
                return 15;
            case 5:
                return 12;
            case 6:
                return 10;
            default:
                return -1;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_setup);
        appBar = findViewById(R.id.setupAppBar);
        setAppBarMenu();

        Player1 = findViewById(R.id.player1name);
        Player2 = findViewById(R.id.player2name);
        Player3 = findViewById(R.id.player3name);
        Player4 = findViewById(R.id.player4name);
        Player5 = findViewById(R.id.player5name);
        Player6 = findViewById(R.id.player6name);

        playerCount = 3;
    }

    @SuppressLint("NonConstantResourceId")
    private void setAppBarMenu() {
        appBar.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.loadSaved:
                    //TODO
                    break;
                case R.id.alternateRules:
                    startActivity(new Intent(getApplicationContext(), AlternateRules.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                    break;
                case R.id.contact:
                    toContactActivity();
                    break;
            }
            return true;
        });
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
                displayError(getString(R.string.max_players));
                break;
            default:
                break;
        }
    }

    public void removePlayer(View v) {
        switch (playerCount) {
            case 3:
                displayError(getString(R.string.min_players));
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

    public void onStartButtonPressed(View v) {
        final String[] playerNames = new String[playerCount];

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
            if (s.isEmpty()) {
                displayError(getString(R.string.playerNameError));
                return;
            }
        }

        if (rulesPrefs.getBoolean(ONE_TO_X, false)) {
            final NumberPicker picker = new NumberPicker(this);
            picker.setMinValue(2);
            picker.setMaxValue(getRoundCount(playerCount));

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(getString(R.string.one_to_x))
                    .setMessage(getString(R.string.max_tricks))
                    .setView(picker)
                    .setNegativeButton(getString(R.string.cancel), null)
                    .setNeutralButton(getString(R.string.change_mode),
                            (dialogInterface, i) -> startActivity(new Intent(getApplicationContext(), AlternateRules.class)))
                    .setPositiveButton(getString(R.string.start),
                            (dialogInterface, i) -> startGame(playerNames, picker.getValue()))
                    .show();
        } else {
            startGame(playerNames, null);
        }
    }

    private void startGame(String[] playerNames, Integer maxTricks) {
        boolean isCdnRule = rulesPrefs.getBoolean(CDN_RULE, false);
        boolean isNoEven = rulesPrefs.getBoolean(NO_EVEN, false);
        int numRounds = maxTricks == null ? getRoundCount(playerCount) : maxTricks * 2 - 1;

        Player[] players = new Player[playerCount];
        for (int i = 0; i < playerCount; i++) {
            players[i] = new Player(playerNames[i]);
        }

        Game game = new Game(numRounds, isCdnRule, isNoEven, maxTricks, players);

        if (saveGame(game)) {
            Intent intent = new Intent(this, Bids.class);
            intent.putExtra(GAME_ID, game.getId());
            startActivity(intent);
        } else {
            displayError(getString(R.string.start_error));
        }
    }
}

