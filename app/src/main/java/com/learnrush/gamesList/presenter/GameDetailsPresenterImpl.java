package com.learnrush.gamesList.presenter;

import android.content.Context;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.learnrush.addgame.model.GameModel;
import com.learnrush.gamesList.model.CommentsModel;
import com.learnrush.gamesList.view.IGameDetailsView;

/**
 * LearnRush Created by Mohammed Fareed on 4/22/2017.
 */

public class GameDetailsPresenterImpl implements IGameDetailsPresenter {
    DatabaseReference mRef = FirebaseDatabase.getInstance().getReference();
    IGameDetailsView mIGameDetailsView;
    Context mContext;

    public GameDetailsPresenterImpl(Context context, IGameDetailsView iGameDetailsView){
        mIGameDetailsView = iGameDetailsView;
        mContext = context;
    }

    @Override
    public void getGameDetails(String gameKey) {
        mRef.child("games").child(gameKey).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                GameModel gameModel = dataSnapshot.getValue(GameModel.class);
                String name = gameModel.getGame_name();
                String desc = gameModel.getGame_desc();
                String category = gameModel.getGame_cateogry();
                String imageUrl = gameModel.getImage();
                mIGameDetailsView.bindView(name, category, desc, imageUrl);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    @Override
    public void onStartGameClicked(String gameKey) {

    }

    @Override
    public void onAddCommentClicked(CommentsModel commentsModel, String gameKey) {
        DatabaseReference gameCommentsRef = FirebaseDatabase.getInstance().getReference().child("game_comments");
        gameCommentsRef.child(gameKey).push().setValue(commentsModel);
    }
}
