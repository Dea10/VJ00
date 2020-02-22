package com.dea.vj00.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import com.dea.vj00.POJOs.Game;
import com.dea.vj00.POJOs.GameListElement;
import com.dea.vj00.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class GameDetailActivity extends AppCompatActivity {

    public FirebaseDatabase database;
    DatabaseReference myRef;

    public TextView tv_game_name;
    public TextView tv_game_status;
    public TextView tv_game_cost;
    public TextView tv_game_credits;
    public RadioButton rb_not_started;
    public RadioButton rb_started;
    public RadioButton rb_finished;

    public String game_id;
    public String game_name;
    public String game_platform;
    public String game_status;
    public Integer game_credits;
    public Double game_cost;

    private String TAG = GameDetailActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_detail);

        Intent intent = getIntent();

        game_id = intent.getStringExtra("EXTRA_GAME_ID");

        bindUI();
        setAdapter();
    }

    public void setAdapter() {
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference().child("games").child(game_id);

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                Game game = dataSnapshot.getValue(Game.class);

                game_name = game.name;
                game_cost = game.cost;
                game_credits = game.credits;
                game_platform = game.platform;
                game_status = game.game_status;

                tv_game_name.setText(game.name);
                tv_game_status.setText("Status: " + game.game_status);
                tv_game_cost.setText("Cost: $" + game.cost.toString());
                tv_game_credits.setText("Credits: " + game.credits.toString());

                switch (game_status) {
                    case "not_started":
                        rb_not_started.toggle();
                        break;
                    case "started":
                        rb_started.toggle();
                        break;
                    case "finished":
                        rb_finished.toggle();
                        break;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        };
        myRef.addValueEventListener(postListener);
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        GameListElement gameListElement = null;
        Game game = null;

        myRef = database.getReference();

        Map<String, Object> gameValues;
        Map<String, Object> gameListElementValues;
        Map<String, Object> childUpdates;

        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_not_started:
                if (checked)
                    game_status = "not_started";
                    // update games
                    game = new Game(game_name, game_platform, game_status, game_credits, game_cost);
                    gameValues = game.toMap();
                    // update game_list
                    gameListElement = new GameListElement(game_name, game_platform, game_status);
                    gameListElementValues = gameListElement.toMap();

                    childUpdates = new HashMap<>();
                    childUpdates.put("/games/" + game_id, gameValues);
                    childUpdates.put("/game_list/" + game_id, gameListElementValues);

                    myRef.updateChildren(childUpdates);
                    break;
            case R.id.radio_started:
                if (checked)
                    game_status = "started";
                    // update games
                    game = new Game(game_name, game_platform, game_status, game_credits, game_cost);
                    gameValues = game.toMap();
                    // update game_list
                    gameListElement = new GameListElement(game_name, game_platform, game_status);
                    gameListElementValues = gameListElement.toMap();

                    childUpdates = new HashMap<>();
                    childUpdates.put("/games/" + game_id, gameValues);
                    childUpdates.put("/game_list/" + game_id, gameListElementValues);

                    myRef.updateChildren(childUpdates);
                    break;
            case R.id.radio_finished:
                if (checked)
                    game_status = "finished";
                    // update games
                    game = new Game(game_name, game_platform, game_status, game_credits, game_cost);
                    gameValues = game.toMap();
                    // update game_list
                    gameListElement = new GameListElement(game_name, game_platform, game_status);
                    gameListElementValues = gameListElement.toMap();

                    childUpdates = new HashMap<>();
                    childUpdates.put("/games/" + game_id, gameValues);
                    childUpdates.put("/game_list/" + game_id, gameListElementValues);

                    myRef.updateChildren(childUpdates);
                    break;
        }
    }

    public void bindUI() {
        tv_game_name = findViewById(R.id.tv_game_name);
        tv_game_status = findViewById(R.id.tv_game_status);
        tv_game_cost = findViewById(R.id.tv_game_cost);
        tv_game_credits = findViewById(R.id.tv_game_credits);
        rb_not_started = findViewById(R.id.radio_not_started);
        rb_started = findViewById(R.id.radio_started);
        rb_finished = findViewById(R.id.radio_finished);
    }
}
