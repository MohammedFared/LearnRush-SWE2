package com.learnrush.presenter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.learnrush.GamesFragment;
import com.learnrush.GamesFragment.OnFragmentInteractionListener;
import com.learnrush.RegisterLoginActivity;

/**
 * LearnRush Created by Mohammed Fareed on 4/12/2017.
 */

public class GamesPresenter implements OnFragmentInteractionListener{
    OnFragmentInteractionListener mListener;
    View mView;
    Context mContext;
    private String TAG = "GamesPresenterLOG";
    public GamesPresenter(OnFragmentInteractionListener listener, View view){
        this.mListener = listener;
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
}
