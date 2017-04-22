package com.learnrush.gameplay.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.learnrush.R;
import com.learnrush.gameplay.presenter.IGamePlayPresenter;

public class GamePlay extends AppCompatActivity implements IGamePlayPresenter{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_play);
    }
}
