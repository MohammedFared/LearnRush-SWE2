package com.learnrush.model;

/**
 * LearnRush Created by Mohammed Fareed on 4/13/2017.
 */

public class GameModel {
    private String teacher_id, cateogry, game_name;
    private int rate_count, rating;

    public GameModel() {
    }

    public GameModel(String teacher_id, String cateogry, String game_name, int rate_count, int rating) {
        this.teacher_id = teacher_id;
        this.cateogry = cateogry;
        this.game_name = game_name;
        this.rate_count = rate_count;
        this.rating = rating;
    }

    public String getTeacher_id() {
        return teacher_id;
    }

    public void setTeacher_id(String teacher_id) {
        this.teacher_id = teacher_id;
    }

    public String getCateogry() {
        return cateogry;
    }

    public void setCateogry(String cateogry) {
        this.cateogry = cateogry;
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
