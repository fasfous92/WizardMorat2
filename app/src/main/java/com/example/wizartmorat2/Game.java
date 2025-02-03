package com.example.wizartmorat2;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Game {
    public String title;
    public Date gameDate;
    public Map<String, Integer> score;
    public Integer numberPlays;
    public Integer numberOfPlayers ;
    Integer numberOfCards = 60;

    // Constructor


    public Game(String jsonString){
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            String title = jsonObject.getString("title");
            JSONArray players = jsonObject.getJSONArray("players");

            this.score = new HashMap<>();

            for (int i = 0; i < players.length(); i++) {
                this.score.put(players.getString(i), 0);
            }
            Integer size = players.length();
            this.numberOfPlayers = size;
            this.title=title;
            this.gameDate = new Date();
            this.numberPlays = numberOfCards / size;

        }catch (Exception e){
            e.printStackTrace();
        }
    }




}
