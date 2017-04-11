package com.learnrush.presenter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.learnrush.GameDetails;
import com.learnrush.R;
import com.learnrush.model.GamesModel;

/**
 * LearnRush Created by Mohammed Fareed on 4/11/2017.
 */

public class GamesPresenterImpl{
    private String TAG = "GamesPresenterImplLOG";

    public static final String CLICKED_GAME_KEY = "game_key";
    // [START define_database_reference]
    private DatabaseReference mDatabase;
    // [END define_database_reference]


    GamePresenter mGamePresenter;

    View mView;

    private FirebaseRecyclerAdapter<GamesModel, GamesViewHolder> mAdapter;

    public GamesPresenterImpl(View view, GamePresenter gamesPresenter){
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mView = view;
        mGamePresenter = gamesPresenter;
        Query query = mDatabase.child("games");
        createAdapterBasedOnQuery(query);
    }

    public FirebaseRecyclerAdapter createAdapterBasedOnQuery(final Query query) {
        Log.d(TAG, "createAdapterBasedOnQuery: " );
        Utils.showProgressDialog(mView.getContext(), "Loading data...");
        mAdapter = new FirebaseRecyclerAdapter<GamesModel, GamesViewHolder>
                (GamesModel.class, R.layout.single_game_item, GamesViewHolder.class, query) {
            @Override
            protected void populateViewHolder(GamesViewHolder viewHolder, GamesModel model, int position) {
                Utils.hideProgressDialog();
                Log.d(TAG, "populateViewHolder: ");
                // On Game Item Click, Go to game Details
                final DatabaseReference clickedGameRef = getRef(position);
                final String gameKey = clickedGameRef.getKey();
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(mView.getContext(), GameDetails.class);
                        intent.putExtra(CLICKED_GAME_KEY, gameKey);
                        mView.getContext().startActivity(intent);
                    }
                });
                viewHolder.bindViewHolder(model);
                mGamePresenter.OnAdapterLoaded(mAdapter);
            }
        };
        Log.d(TAG, "createAdapterBasedOnQuery: end");
        return mAdapter;
    }

    public interface GamePresenter {
        void OnAdapterLoaded(FirebaseRecyclerAdapter adapter);
    }


    public static class GamesViewHolder extends RecyclerView.ViewHolder {
        private String TAG = "GamesViewHolderLOG";
        private ImageView gameImage;
        private TextView nameTV, categoryTV, ratingTV;

        public GamesViewHolder(View itemView) {
            super(itemView);

            nameTV = (TextView) itemView.findViewById(R.id.tv_game_name);
            categoryTV = (TextView) itemView.findViewById(R.id.tv_cateogry);
            gameImage = (ImageView) itemView.findViewById(R.id.img_game);
            ratingTV = (TextView) itemView.findViewById(R.id.tv_rating);
        }

        public void bindViewHolder(GamesModel model){
            Log.d(TAG, "bindViewHolder: " + model.getGame_name());
            nameTV.setText(model.getGame_name());
            categoryTV.setText(model.getCategory());
            ratingTV.setText(String.valueOf(model.getRating()));
        }
    }
}
