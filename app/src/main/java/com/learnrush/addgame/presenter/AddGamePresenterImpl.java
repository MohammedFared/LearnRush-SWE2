package com.learnrush.addgame.presenter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.learnrush.Utils;
import com.learnrush.addgame.model.GameModel;
import com.learnrush.addgame.model.GameQuestionsModel;
import com.learnrush.gamesList.view.Home;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * LearnRush Created by Mohammed Fareed on 4/14/2017.
 */

public class AddGamePresenterImpl implements IAddGamePresenter {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    Context mContext;
    private StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();
    private String TAG = "AddGamePresenterImplLOG";

    public AddGamePresenterImpl(Context context){
        mContext = context;
    }

    private String uploadGameImage(String imagePath, String gameKey){
        final String[] downloadUrlar = new String[1];

        Uri uri = Uri.parse(imagePath);
        mStorageRef.child("gameImages").child(gameKey).putFile(uri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get a URL to the uploaded content
                        String downloadUrl = taskSnapshot.getDownloadUrl().toString();
                        downloadUrlar[0] = downloadUrl;
                        Log.d(TAG, "onSuccess: " + downloadUrl);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Log.e(TAG, "onFailure: ", exception);
                        // Handle unsuccessful uploads
                        // ...
                    }
                });
        Log.d(TAG, "uploadGameImage: " + downloadUrlar[0]);
        return downloadUrlar[0];
    }

    @Override
    public void onAddGameClicked(ArrayList<GameQuestionsModel> mGameQuestionsArrayList, GameModel gameDetailsModel) {
        //COMPLETED: add the data to firebase and return to home

        Map<String, Object> childUpdates = new HashMap<>();
        //*** START Add game details
        String uId = Utils.user.getUid();

        gameDetailsModel.setTeacher_id(uId); //add the teacher id to the model
        String gameKey = myRef.child("games").push().getKey();
        uploadGameImage(gameDetailsModel.getImage(), gameKey);
        Map<String, Object> gameValues = gameDetailsModel.toMap();

        childUpdates.put("/games/" + gameKey, gameValues);
        childUpdates.put("/user_games/" + uId + "/" + gameKey, gameValues);
        //*** END Add game details

        //*** START Add game questions
        Map<String, Object> answersMap = new HashMap<>();
        Map<String, Object> questionsMap = new HashMap<>();
        int i=0;
        for (GameQuestionsModel gameQuestionsModel : mGameQuestionsArrayList){
            answersMap.put(i+"", gameQuestionsModel.getAnswer());
            questionsMap.put(gameQuestionsModel.getQuestion(), gameQuestionsModel.getAnswer());
        }

        childUpdates.put("/game_answers/" + gameKey, answersMap);
        childUpdates.put("/game_questions/" + gameKey, questionsMap);
        //*** END Add game questions

        // Push the map to firebase all at Once
        myRef.updateChildren(childUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (!task.isSuccessful())
                    Toast.makeText(mContext, "The game is not added, Please check your connection and try again", Toast.LENGTH_SHORT).show();
                else {
                    Toast.makeText(mContext, "Game added successfully", Toast.LENGTH_SHORT).show();
                    //TODO: return to home
                    mContext.startActivity(new Intent(mContext, Home.class));
                }
            }
        });
    }

}
