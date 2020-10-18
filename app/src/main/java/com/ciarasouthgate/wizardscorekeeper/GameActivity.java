package com.ciarasouthgate.wizardscorekeeper;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.google.gson.Gson;

import androidx.appcompat.app.AppCompatActivity;

import static com.ciarasouthgate.wizardscorekeeper.Constants.RULES_PREF;
import static com.ciarasouthgate.wizardscorekeeper.Constants.SAVED_GAMES;

public abstract class GameActivity extends AppCompatActivity {
    static final Gson gson = new Gson();

    SharedPreferences rulesPrefs;
    SharedPreferences savedGames;

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
        rulesPrefs = getSharedPreferences(RULES_PREF, MODE_PRIVATE);
        savedGames = getSharedPreferences(SAVED_GAMES, MODE_PRIVATE);
    }

    Game readGame(String gameId) {
        String json = savedGames.getString(gameId, null);
        if (json == null || json.isEmpty()) {
            setResult(RESULT_CANCELED);
            finish();
        }
        return gson.fromJson(json, Game.class);
    }

    boolean saveGame(Game game) {
        System.out.println(gson.toJson(game));
        game.updateSave();
        SharedPreferences.Editor editor = savedGames.edit();
        editor.putString(game.getId(), gson.toJson(game));
        return editor.commit();
    }

    void clearFocus() {
        View v = getCurrentFocus();
        if (v != null)
            v.clearFocus();
    }

    void toContactActivity() {
        startActivity(new Intent(getApplicationContext(), ContactDev.class));
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }
}
