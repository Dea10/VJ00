package com.dea.vj00;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements GameListAdapter.ItemClickListener {

    private RecyclerView recyclerView;
    //private RecyclerView.Adapter mAdapter;
    GameListAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private Context mContext = this;



    public ArrayList<Game> games = new ArrayList<>();
    public ArrayList<String> titulos = new ArrayList<>();
    public ArrayList<String> id = new ArrayList<>();

    private String TAG = MainActivity.class.getName();
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
        Toast.makeText(this, "Clicked: " + mAdapter.getItem(position), Toast.LENGTH_SHORT).show();
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
        DatabaseReference myRef = database.getReference("titulo_juego");

        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.d(TAG, "onChildAdded: " + dataSnapshot.getKey());


                String incoming = dataSnapshot.getValue(String.class);
                game_id = dataSnapshot.getKey();
                //Log.d(TAG, incoming);
                titulos.add(incoming);
                id.add(game_id);
                mAdapter = new GameListAdapter(mContext, titulos, id);
                mAdapter.setClickListener(test);
                recyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Log.d(TAG, "onChildChanged: " + dataSnapshot.getKey());
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
