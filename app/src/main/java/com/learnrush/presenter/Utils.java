package com.learnrush.presenter;

import android.app.ProgressDialog;
import android.content.Context;

import com.google.firebase.auth.FirebaseUser;

/**
 * LearnRush Created by Mohammed Fareed on 4/10/2017.
 */

class Utils {
    static FirebaseUser user;

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
}
