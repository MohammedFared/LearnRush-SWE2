package com.learnrush.gamesList.model;

/**
 * LearnRush Created by Mohammed Fareed on 4/11/2017.
 */

public class GamesModel {
    private String added_by, category, game_name, image_url;
    private int rating;

    public GamesModel(){}

    public GamesModel(String added_by, String category, String game_name, int rating) {
        this.added_by = added_by;
        this.category = category;
        this.game_name = game_name;
        this.rating = rating;
    }

    public String getAdded_by() {
        return added_by;
    }

    public void setAdded_by(String added_by) {
        this.added_by = added_by;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getGame_name() {
        return game_name;
    }

    public void setGame_name(String game_name) {
        this.game_name = game_name;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
