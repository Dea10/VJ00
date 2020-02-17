package com.dea.vj00.POJOs;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class GameListElement {

    public String game_name;
    public String game_platform;
    public String game_status;

    public GameListElement() {}

    public GameListElement(String game_name, String game_platform, String game_status) {
        this.game_name = game_name;
        this.game_platform = game_platform;
        this.game_status = game_status;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("game_name", game_name);
        result.put("game_platform", game_platform);
        result.put("game_status", game_status);

        return result;
    }
}
