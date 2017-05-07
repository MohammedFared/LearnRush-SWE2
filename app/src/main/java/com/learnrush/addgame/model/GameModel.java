package com.learnrush.addgame.model;

import android.net.Uri;

import java.util.HashMap;
import java.util.Map;

/**
 * LearnRush Created by Mohammed Fareed on 4/13/2017.
 */

public class GameModel {
    private String teacher_id, game_cateogry, game_name, game_desc, image;
    private int rate_count, rating;

    public GameModel() {
    }

    public GameModel(String teacher_id, String game_cateogry, String game_name,
                     String game_desc, int rate_count, int rating, String image) {
        this.teacher_id = teacher_id;
        this.game_cateogry = game_cateogry;
        this.game_name = game_name;
        this.rate_count = rate_count;
        this.rating = rating;
        this.game_cateogry = game_cateogry;
    }


    public Map<String,Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("teacher_id", teacher_id);
        result.put("game_cateogry", game_cateogry);
        result.put("game_name", game_name);
        result.put("game_desc", game_desc);
        result.put("image", image);
        result.put("rate_count", 0);
        result.put("rating", 0);

        return result;
    }

    public GameModel(String game_name, String game_cateogry, String game_desc, Uri image){
        this.game_cateogry = game_cateogry;
        this.game_name = game_name;
        this.game_desc = game_desc;
        this.image = String.valueOf(image);
    }

    public String getTeacher_id() {
        return teacher_id;
    }

    public void setTeacher_id(String teacher_id) {
        this.teacher_id = teacher_id;
    }

    public String getGame_cateogry() {
        return game_cateogry;
    }

    public void setGame_cateogry(String game_cateogry) {
        this.game_cateogry = game_cateogry;
    }

    public String getGame_name() {
        return game_name;
    }

    public void setGame_name(String game_name) {
        this.game_name = game_name;
    }

    public int getRate_count() {
        return rate_count;
    }

    public void setRate_count(int rate_count) {
        this.rate_count = rate_count;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getGame_desc() {
        return game_desc;
    }

    public void setGame_desc(String game_desc) {
        this.game_desc = game_desc;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
