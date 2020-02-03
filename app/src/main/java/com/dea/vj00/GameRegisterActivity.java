package com.dea.vj00;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class GameRegisterActivity extends AppCompatActivity {

    public EditText et_game_name;
    public EditText et_platform;
    public EditText et_purchase_date;
    public EditText et_game_cost;
    public EditText et_game_credits;

    public final FirebaseDatabase database = FirebaseDatabase.getInstance();
    public DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    public DatabaseReference gameRef = database.getReference("juegos");
    public DatabaseReference gameListRef = database.getReference("titulo_juego");
    public DatabaseReference gameCostRef = database.getReference("costo_juego");
    public DatabaseReference gameCreditsRef = database.getReference("creditos_juego");

    public String ref = "/juegos/";
    public String listRef = "/titulo_juego/";
    public String costRef = "/costo_juego/";
    public String creditsRef = "/creditos_juego/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_register);

        bindUI();
    }

    public void bindUI() {
        et_game_name = findViewById(R.id.et_game_name);
        et_platform = findViewById(R.id.et_platform);
        et_purchase_date = findViewById(R.id.et_purchase_date);
        et_game_cost = findViewById(R.id.et_game_cost);
        et_game_credits = findViewById(R.id.et_game_credits);
    }

    public void writeNewGame(View view) {
        String key;
        String game_name;
        String platform;
        //Date purchase_date = new Date(1970,01,01);
        Double game_cost;
        Integer game_credits;

        game_name = et_game_name.getText().toString();
        platform = et_platform.getText().toString();
        game_cost = Double.valueOf(et_game_cost.getText().toString());
        game_credits = Integer.valueOf(et_game_credits.getText().toString());

        Game game = new Game(game_name, platform, false, game_credits, game_cost);
        //key = game_name;
        key = mDatabase.child("juegos").push().getKey();

        Map<String, Object> gameValues = game.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put(ref + key, gameValues);
        childUpdates.put(listRef + key, game_name);
        childUpdates.put(costRef + key, game_cost);
        childUpdates.put(creditsRef + key, game_credits);

        mDatabase.updateChildren(childUpdates);

        /*
        gameRef.push().setValue(game);
        gameListRef.push().setValue(game_name);
        gameCostRef.push().setValue(game_cost);
        gameCreditsRef.push().setValue(game_credits);
         */
    }
}
