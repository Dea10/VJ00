package com.dea.vj00.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.dea.vj00.Adapters.GameListAdapter;
import com.dea.vj00.POJOs.Game;
import com.dea.vj00.POJOs.GameListElement;
import com.dea.vj00.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class GameListActivity extends AppCompatActivity implements GameListAdapter.ItemClickListener {

    private RecyclerView recyclerView;
    GameListAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private Context mContext = this;



    public ArrayList<Game> games = new ArrayList<>();
    public ArrayList<GameListElement> games_array_list = new ArrayList<>();
    public ArrayList<String> ids_array_list = new ArrayList<>();

    private String TAG = GameListActivity.class.getName();
    public String game_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setAdapter();
    }

    public void gameRegister(View view) {
        Intent intent = new Intent(this, GameRegisterActivity.class);
        startActivity(intent);
    }

    @Override
    public void onItemClick(View view, int position) {
        // Toast.makeText(this, "Clicked: " + mAdapter.getItem(position), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, GameDetailActivity.class);
        intent.putExtra("EXTRA_GAME_ID", mAdapter.getItem(position));
        startActivity(intent);
    }

    public void setAdapter() {
        recyclerView = findViewById(R.id.rv_game_list);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        final GameListAdapter.ItemClickListener test = this;

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("game_list");

        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.d(TAG, "onChildAdded: " + dataSnapshot.getKey());

                GameListElement incoming = dataSnapshot.getValue(GameListElement.class);
                game_id = dataSnapshot.getKey();
                games_array_list.add(incoming);
                ids_array_list.add(game_id);
                mAdapter = new GameListAdapter(mContext, games_array_list, ids_array_list);
                mAdapter.setClickListener(test);
                recyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Log.d(TAG, "onChildChanged: " + dataSnapshot.getKey());

                //GameListElement gle = new GameListElement("Metal Slug 7", "Nintendo DS", "started");

                GameListElement incoming = dataSnapshot.getValue(GameListElement.class);
                game_id = dataSnapshot.getKey();

                int pos = ids_array_list.indexOf(game_id);

                games_array_list.set(pos, incoming);

                //games_array_list.add(incoming);
                //ids_array_list.add(game_id);
                //mAdapter = new GameListAdapter(mContext, games_array_list, ids_array_list);
                //mAdapter.setClickListener(test);
                //recyclerView.setAdapter(mAdapter);

                mAdapter = new GameListAdapter(mContext, games_array_list, ids_array_list);
                mAdapter.setClickListener(test);
                recyclerView.setAdapter(mAdapter);

                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.d(TAG, "onChildRemoved: " + dataSnapshot.getKey());
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                Log.d(TAG, "onChildMoved: " + dataSnapshot.getKey());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "postComments:onCancelled " + databaseError.toException());
            }
        };
        myRef.addChildEventListener(childEventListener);
    }
}
