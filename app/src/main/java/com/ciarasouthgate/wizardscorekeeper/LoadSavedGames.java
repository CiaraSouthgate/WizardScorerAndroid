package com.ciarasouthgate.wizardscorekeeper;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.ciarasouthgate.wizardscorekeeper.Constants.GAME_ID;
import static com.ciarasouthgate.wizardscorekeeper.Constants.prettyPrintDate;

public class LoadSavedGames extends AppActivity {
    private ArrayList<Game> games;
    private RecyclerView recyclerView;
    private SavedGameAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private TextView noSavesText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_saved_games);

        appBar = findViewById(R.id.appBar);
        recyclerView = findViewById(R.id.saveList);
        noSavesText = findViewById(R.id.noSavesText);

        setBackOnlyAppBar();


        readGames();
        if (games.size() > 0)
            noSavesText.setVisibility(View.GONE);

        setRecyclerView();
    }

    void readGames() {
        Map<String, ?> gamesMap = savedGames.getAll();
        games = new ArrayList<>();
        gamesMap.forEach((gameId, gameStr) -> games.add(gson.fromJson((String) gameStr, Game.class)));
    }

    void onDeletePressed(int i) {
        deleteSave(games.get(i).getId());
        games.remove(i);
        adapter.notifyItemRemoved(i);
        adapter.notifyItemRangeChanged(i, games.size());

        if (games.size() == 0)
            noSavesText.setVisibility(View.VISIBLE);
    }

    private void setRecyclerView() {
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new SavedGameAdapter(games, this::onDeletePressed);

        adapter.setOnItemClickListener((position, v) -> {
            Game game = games.get(position);
            Intent intent = new Intent(getApplicationContext(),
                    game.isNewRound() ? Bids.class : Tricks.class);
            intent.putExtra(GAME_ID, game.getId());
            startActivity(intent);
        });

        recyclerView.setAdapter(adapter);
    }

    interface DeleteListener {
        void onDelete(int position);
    }

    static class SavedGameAdapter extends RecyclerView.Adapter<SavedGameAdapter.SavedGameHolder> {
        private static SavedGameAdapter.ClickListener clickListener;
        private final ArrayList<Game> savedGames;
        private final DeleteListener deleteListener;

        public SavedGameAdapter(ArrayList<Game> games, DeleteListener deleteListener) {
            this.savedGames = games;
            this.deleteListener = deleteListener;
        }

        public void setOnItemClickListener(SavedGameAdapter.ClickListener clickListener) {
            SavedGameAdapter.clickListener = clickListener;
        }

        @Override
        public int getItemCount() {
            return savedGames.size();
        }

        @Override
        public void onBindViewHolder(SavedGameHolder savedGameHolder, int i) {
            Game game = savedGames.get(i);
            String playerNames = Arrays.stream(game.getPlayers()).map(Player::getName).collect(Collectors.joining());
            String roundString = savedGameHolder.itemView.getContext()
                    .getString(R.string.round_title, game.getCurrentRound(), game.getNumRounds());

            savedGameHolder.timestamp.setText(prettyPrintDate(game.getLastSave()));
            savedGameHolder.playerList.setText(playerNames);
            savedGameHolder.roundTitle.setText(roundString);
            savedGameHolder.deleteButton.setOnClickListener(v -> {
                deleteListener.onDelete(i);
            });
        }

        @NonNull
        @Override
        public SavedGameHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View itemView = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.card_saved_game, viewGroup, false);
            return new SavedGameHolder(itemView);
        }

        public interface ClickListener {
            void onItemClick(int position, View v);
        }

        public static class SavedGameHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            protected TextView timestamp;
            protected TextView playerList;
            protected TextView roundTitle;
            protected ImageView deleteButton;

            public SavedGameHolder(View v) {
                super(v);
                timestamp = v.findViewById(R.id.timestamp);
                playerList = v.findViewById(R.id.playersList);
                roundTitle = v.findViewById(R.id.roundInfo);
                deleteButton = v.findViewById(R.id.deleteButton);
                v.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                clickListener.onItemClick(getAdapterPosition(), v);
            }
        }
    }
}