package com.dea.vj00;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class GameDetailActivity extends AppCompatActivity {

    public TextView tv_game_name;
    public TextView tv_game_status;
    public TextView tv_game_cost;
    public TextView tv_game_credits;

    public String id;

    private String TAG = GameDetailActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_detail);

        Intent intent = getIntent();

        id = intent.getStringExtra("EXTRA_GAME_ID");
        Log.d(TAG, id);

        setAdapter();

        tv_game_name = findViewById(R.id.tv_game_name);
        tv_game_status = findViewById(R.id.tv_game_status);
        tv_game_cost = findViewById(R.id.tv_game_cost);
        tv_game_credits = findViewById(R.id.tv_game_credits);
    }

    public void setAdapter() {


        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference myRef = database.getReference().child("juegos").child(id);

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                Game game = dataSnapshot.getValue(Game.class);
                tv_game_name.setText(game.name);
                tv_game_status.setText("Finished: " + game.isFinished.toString());
                tv_game_cost.setText("Cost: $" + game.cost.toString());
                tv_game_credits.setText("Credits: " + game.credits.toString());
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
}
