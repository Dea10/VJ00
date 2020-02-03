package com.dea.vj00;

import java.util.HashMap;
import java.util.Map;

public class Game {
    public String name;
    public String platform;
    public Boolean isFinished;
    public Integer credits;
    public Double cost;

    public Game() {}

    public Game(String name, String platform, Boolean isFinished, Integer credits, Double cost) {
        this.name = name;
        this.platform = platform;
        this.isFinished = isFinished;
        this.credits = credits;
        this.cost = cost;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("name", name);
        result.put("platform", platform);
        result.put("isFinished", isFinished);
        result.put("credits", credits);
        result.put("cost", cost);

        return result;
    }
}
