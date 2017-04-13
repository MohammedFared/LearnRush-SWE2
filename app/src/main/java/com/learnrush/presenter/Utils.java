package com.learnrush.presenter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;
import com.learnrush.model.UserModel;

import static android.content.Context.MODE_PRIVATE;

/**
 * LearnRush Created by Mohammed Fareed on 4/10/2017.
 */

class Utils {
    static FirebaseUser user; // used to get the UID
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    static String USERSHAREDPREFS = "USERSHAREDPREFS";

    static boolean isEmpty(String s) {
        return (s.trim().length() == 0);
    }


    private static ProgressDialog mProgressDialog;
    static void showProgressDialog(Context context, String message) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(context);
            mProgressDialog.setMessage(message);
            mProgressDialog.setIndeterminate(true);
            mProgressDialog.setCancelable(false);
        }
        mProgressDialog.show();
    }
    static void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    static UserModel getUserData(Context context){
        SharedPreferences mPrefs = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String json = mPrefs.getString(USERSHAREDPREFS, null);
        UserModel userModel = new Gson().fromJson(json, UserModel.class);
        return userModel;
    }
}
