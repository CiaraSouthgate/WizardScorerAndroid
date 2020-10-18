package com.ciarasouthgate.wizardscorekeeper;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import static com.ciarasouthgate.wizardscorekeeper.Constants.RULES_PREF;
import static com.ciarasouthgate.wizardscorekeeper.Constants.SAVED_GAMES;

public abstract class AppActivity extends AppCompatActivity {
    static final Gson gson = new Gson();

    SharedPreferences rulesPrefs;
    SharedPreferences savedGames;

    Toolbar appBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rulesPrefs = getSharedPreferences(RULES_PREF, MODE_PRIVATE);
        savedGames = getSharedPreferences(SAVED_GAMES, MODE_PRIVATE);
    }

    abstract void setAppBarMenu();

    void displayError(String error) {
        Toast.makeText(getApplicationContext(), error, Toast.LENGTH_LONG).show();
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

    void toGameSetup() {
        Intent intent = new Intent(getApplicationContext(), GameSetup.class);
        startActivity(intent);
    }

    void toContactActivity() {
        startActivity(new Intent(getApplicationContext(), ContactDev.class));
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }
}
