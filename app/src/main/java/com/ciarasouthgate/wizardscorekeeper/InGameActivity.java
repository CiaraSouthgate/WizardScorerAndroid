package com.ciarasouthgate.wizardscorekeeper;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import static com.ciarasouthgate.wizardscorekeeper.Constants.GAME_ID;

public abstract class InGameActivity extends AppActivity {
    String gameId;
    Game game;
    Player[] players;

    TableRow[] rows;
    TextView[] playerNames;
    TextView[] scores;

    TableLayout table;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        gameId = getIntent().getStringExtra(GAME_ID);
        game = readGame(gameId);

        players = game.getPlayers();

        setLayout();
        getViews();
        getGameInfo();
        setAppBarMenu();
        setTitles();
        setArrays();
        setPlayers();
        showRows();
    }

    abstract void setLayout();

    abstract void getViews();

    abstract void getGameInfo();

    abstract void setTitles();

    abstract void setArrays();

    abstract void setPlayers();

    @SuppressLint("NonConstantResourceId")
    private void setAppBarMenu() {
        appBar.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.scorecard:
                    //TODO create scorecard
                    break;
                case R.id.newGame:
                    newGameDialog(getString(R.string.new_game)).show();
                    break;
                case R.id.loadSaved:
                    //TODO implement loading of saved games
                    break;
                case R.id.contact:
                    toContactActivity();
                    break;
                default:
                    break;
            }
            return true;
        });
    }

    void showRows() {
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

    AlertDialog.Builder newGameDialog(String title) {
        return new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(getString(R.string.save_game_dialog))
                .setPositiveButton(getString(R.string.save), (dialog, which) -> {
                    saveGame(game);
                    toGameSetup();
                })
                .setNegativeButton(getString(R.string.discard), (dialog, which) -> {
                    savedGames.edit()
                            .remove(game.getId())
                            .apply();
                    toGameSetup();
                })
                .setNeutralButton(getString(R.string.cancel),
                        (dialog, which) -> dialog.cancel());
    }
}
