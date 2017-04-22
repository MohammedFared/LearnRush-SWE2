package com.learnrush;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;
import com.learnrush.loginandregister.model.UserModel;

import static android.content.Context.MODE_PRIVATE;

/**
 * LearnRush Created by Mohammed Fareed on 4/10/2017.
 */
public class Utils {
    public static FirebaseUser user; // used to get the UID
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    public static final String USERSHAREDPREFS = "USERSHAREDPREFS";

    public static UserModel getUserData(Context context){
        SharedPreferences mPrefs = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String json = mPrefs.getString(USERSHAREDPREFS, null);
        UserModel userModel = new Gson().fromJson(json, UserModel.class);
        return userModel;
    }

}
