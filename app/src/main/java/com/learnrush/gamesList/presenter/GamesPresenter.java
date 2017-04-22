package com.learnrush.gamesList.presenter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.learnrush.Utils;
import com.learnrush.gamesList.view.GameDetails;
import com.learnrush.gamesList.view.GamesFragment;
import com.learnrush.loginandregister.view.RegisterLoginActivity;

/**
 * LearnRush Created by Mohammed Fareed on 4/12/2017.
 */

public class GamesPresenter implements IGamesPresenter {
    public static String CLICKED_GAME_KEY = "game_key";
    View mView;
    Context mContext;
    private String TAG = "GamesPresenterLOG";
    public GamesPresenter(View view){
        this.mView = view;
        this.mContext = mView.getContext();
        Log.d(TAG, "GamesPresenter: " + String.valueOf(Utils.getUserData(mContext).getName()));
        if (Utils.getUserData(mContext).getIsTeacher())
            GamesFragment.showAddGameFAB();
        else
            GamesFragment.hideAddGameFAB();
    }

    @Override
    public void onLogOut() {
        // signout using firebase
        FirebaseAuth.getInstance().signOut();

        SharedPreferences.Editor editor = mContext.getSharedPreferences(Utils.MY_PREFS_NAME, Context.MODE_PRIVATE).edit();
        editor.remove(Utils.USERSHAREDPREFS);
        editor.commit();
        // return to register Activity
        mContext.startActivity(new Intent(mContext, RegisterLoginActivity.class));
    }

    @Override
    public void OnGameClicked(String gameKey) {
        Intent intent = new Intent(mView.getContext(), GameDetails.class);
        intent.putExtra(CLICKED_GAME_KEY, gameKey);
        mView.getContext().startActivity(intent);
    }
}
