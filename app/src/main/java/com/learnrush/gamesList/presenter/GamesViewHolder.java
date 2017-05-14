package com.learnrush.gamesList.presenter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.learnrush.R;
import com.learnrush.addgame.model.GameModel;
import com.squareup.picasso.Picasso;

/**
 * LearnRush Created by Mohammed Fareed on 4/11/2017.
 */

    public class GamesViewHolder extends RecyclerView.ViewHolder {
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

        public void bindViewHolder(GameModel model){
            Log.d(TAG, "bindViewHolder: " + model.getGame_name());
            nameTV.setText(model.getGame_name());
            categoryTV.setText(model.getGame_cateogry());
            ratingTV.setText(String.valueOf(model.getRating()));
            Picasso.with(itemView.getContext())
                    .load(model.getImage())
                    .into(gameImage);
        }
    }
