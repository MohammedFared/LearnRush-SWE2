package com.learnrush.gamesList.view;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.util.Log;

import com.learnrush.R;

import java.util.Date;

public class Home extends AppCompatActivity {

    private String TAG = "HOMELOG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Log.d(TAG, "onCreate: " + new Date().getTime());
        Log.d(TAG, "onCreate: " + DateFormat.format("(HH:mm)", new Date().getTime()));
    }
}
