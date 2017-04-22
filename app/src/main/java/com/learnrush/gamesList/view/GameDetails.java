package com.learnrush.gamesList.view;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.learnrush.R;
import com.learnrush.addgame.model.GameModel;
import com.learnrush.gamesList.presenter.GameDetailsPresenterImpl;
import com.learnrush.gamesList.presenter.GamesPresenter;
import com.learnrush.gamesList.presenter.IGameDetailsPresenter;
import com.squareup.picasso.Picasso;


public class GameDetails extends AppCompatActivity implements IGameDetailsView{
    TextView categoryTextView, nameTextView, descriptionTextView;
    ImageView imageView;
    IGameDetailsPresenter mIGameDetailsPresenter;
    private String TAG = "GameDetailsLOG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_details);
        String gameKey = "";
        if (getIntent() != null)
            gameKey = getIntent().getStringExtra(GamesPresenter.CLICKED_GAME_KEY);

        categoryTextView = (TextView) findViewById(R.id.tv_game_category);
        nameTextView = (TextView) findViewById(R.id.tv_game_name);
        descriptionTextView = (TextView) findViewById(R.id.tv_game_desc);
        imageView = (ImageView) findViewById(R.id.img_game);

        mIGameDetailsPresenter = new GameDetailsPresenterImpl(this, this);
        Log.d(TAG, "onCreate: " + gameKey);
        getGameDetails(gameKey);
    }


    DatabaseReference mRef = FirebaseDatabase.getInstance().getReference();
    public void getGameDetails(String gameKey) {
        mRef.child("games").child(gameKey).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                GameModel gameModel = dataSnapshot.getValue(GameModel.class);
                String name = gameModel.getGame_name();
                String desc = gameModel.getGame_desc();
                String category = gameModel.getGame_cateogry();
                String imageUrl = gameModel.getImage();
                bindView(name, category, desc, imageUrl);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    @Override
    public void bindView(String name, String categ, String desc, String imgurl) {
        Log.d(TAG, "bindView: " + categ);
        categoryTextView.setText(categ);
        descriptionTextView.setText(desc);
        nameTextView.setText(name);
        Picasso.with(this)
                .load(imgurl)
                .into(imageView);
    }

    public void onPlayGame(View view) {

    }
}
