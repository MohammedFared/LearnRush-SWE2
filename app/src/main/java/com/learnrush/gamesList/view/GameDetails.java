package com.learnrush.gamesList.view;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.learnrush.R;
import com.learnrush.Utils;
import com.learnrush.addgame.model.GameModel;
import com.learnrush.gameplay.view.GamePlay;
import com.learnrush.gamesList.model.CommentsModel;
import com.learnrush.gamesList.presenter.CommentsViewHolder.CommentsViewHolder;
import com.learnrush.gamesList.presenter.GameDetailsPresenterImpl;
import com.learnrush.gamesList.presenter.GamesPresenter;
import com.learnrush.gamesList.presenter.IGameDetailsPresenter;
import com.squareup.picasso.Picasso;


public class GameDetails extends AppCompatActivity implements IGameDetailsView{
    TextView categoryTextView, nameTextView, descriptionTextView;
    ImageView imageView;
    IGameDetailsPresenter mIGameDetailsPresenter;
    private String TAG = "GameDetailsLOG", gameKey;
    private Button addCommentBtn;
    private EditText commentEditText;
    private RecyclerView mCommentsRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_details);
        if (getIntent() != null)
            gameKey = getIntent().getStringExtra(GamesPresenter.CLICKED_GAME_KEY);

        categoryTextView = (TextView) findViewById(R.id.tv_game_category);
        nameTextView = (TextView) findViewById(R.id.tv_game_name);
        descriptionTextView = (TextView) findViewById(R.id.tv_game_desc);
        imageView = (ImageView) findViewById(R.id.img_game);

        addCommentBtn = (Button) findViewById(R.id.btn_add_comment);
        commentEditText = (EditText) findViewById(R.id.et_add_comment);

        mCommentsRecyclerView = (RecyclerView) findViewById(R.id.rv_game_comments);
        mCommentsRecyclerView.setHasFixedSize(true);

        addCommentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CommentsModel commentsModel = new CommentsModel(commentEditText.getText().toString(),
                        Utils.getUserData(GameDetails.this).getName(), FirebaseAuth.getInstance().getCurrentUser().getUid());
                mIGameDetailsPresenter.onAddCommentClicked(commentsModel, gameKey);
                commentEditText.setText("");
            }
        });

        populateGameCommentsRecyclerView(gameKey);

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
        categoryTextView.setText(categ);
        descriptionTextView.setText(desc);
        nameTextView.setText(name);
        Picasso.with(this)
                .load(imgurl)
                .into(imageView);
    }

    private void populateGameCommentsRecyclerView(String gameKey){
        LinearLayoutManager layoutManager = new LinearLayoutManager(GameDetails.this, LinearLayoutManager.VERTICAL, false);
        mCommentsRecyclerView.setLayoutManager(layoutManager);
        DatabaseReference gameCommentsRef = FirebaseDatabase.getInstance().getReference().child("game_comments").child(gameKey);
        FirebaseRecyclerAdapter<CommentsModel, CommentsViewHolder> commentsAdapter =
                new FirebaseRecyclerAdapter<CommentsModel, CommentsViewHolder>(CommentsModel.class, R.layout.comment_layout
                , CommentsViewHolder.class, gameCommentsRef) {
            @Override
            protected void populateViewHolder(CommentsViewHolder viewHolder, CommentsModel model, int position) {
                viewHolder.bind(model);
            }
        };
        mCommentsRecyclerView.setAdapter(commentsAdapter);
    }

    public void onPlayGame(View view) {
        startActivity(new Intent(this, GamePlay.class).putExtra(GamesPresenter.CLICKED_GAME_KEY, gameKey));
        finish();
    }
}
