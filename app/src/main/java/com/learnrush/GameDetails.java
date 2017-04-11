package com.learnrush;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.learnrush.presenter.GamesPresenterImpl;

public class GameDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_details);
        String gameKey = null;
         if(getIntent() != null){
             gameKey = getIntent().getStringExtra(GamesPresenterImpl.CLICKED_GAME_KEY);
         }

        TextView tv= (TextView) findViewById(R.id.tv_game_key);
        tv.setText(gameKey);
    }
}
