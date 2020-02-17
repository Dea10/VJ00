package com.dea.vj00.POJOs;

import java.util.HashMap;
import java.util.Map;

public class Game {
    public String name;
    public String platform;
    public String game_status;
    public Integer credits;
    public Double cost;

    public Game() {}

    public Game(String name, String platform, String game_status, Integer credits, Double cost) {
        this.name = name;
        this.platform = platform;
        this.game_status = game_status;
        this.credits = credits;
        this.cost = cost;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("name", name);
        result.put("platform", platform);
        result.put("game_status", game_status);
        result.put("credits", credits);
        result.put("cost", cost);

        return result;
    }
}
