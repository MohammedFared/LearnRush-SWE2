package com.learnrush.gamesList.model;

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

    public GameModel(String game_name, String game_cateogry, String game_desc, String image){
        this.game_cateogry = game_cateogry;
        this.game_name = game_name;
        this.game_desc = game_desc;
        this.image = image;
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
}
