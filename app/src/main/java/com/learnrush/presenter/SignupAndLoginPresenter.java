package com.learnrush.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.learnrush.Home;
import com.learnrush.model.UserModel;

import static android.content.Context.MODE_PRIVATE;
import static com.learnrush.presenter.Utils.MY_PREFS_NAME;
import static com.learnrush.presenter.Utils.USERSHAREDPREFS;
import static com.learnrush.presenter.Utils.getUserData;
import static com.learnrush.presenter.Utils.hideProgressDialog;
import static com.learnrush.presenter.Utils.isEmpty;
import static com.learnrush.presenter.Utils.showProgressDialog;
import static com.learnrush.presenter.Utils.user;

/**
 * LearnRush Created by Mohammed Fareed on 4/9/2017.
 */

public class SignupAndLoginPresenter implements RegisterInterFace {
    private final Context mContext;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("users");

    private String TAG = "SignupPresenterLOG";

    View mView;
    private String TEACHEREMAIL = "@teacher.com";

    public SignupAndLoginPresenter(View signUpView, Context context) {
        mView = signUpView;
        mContext = context;
    }

    @Override
    public void onCreate() {
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {  // User is signed in
                    Utils.user = user;
                    if(!user.isEmailVerified())
                        Snackbar.make(mView, "Verify your email, Please", BaseTransientBottomBar.LENGTH_INDEFINITE).show();

                    Log.d(TAG, "onAuthStateChanged:signed_in: " + user.getUid());
                    if (getUserData(mContext) != null) {
                        // : continue to Home
                        Log.d(TAG, "onAuthStateChanged: NOT NULL");
                        mView.getContext().startActivity(new Intent(mView.getContext(), Home.class));
                    }else {
                        Log.d(TAG, "onAuthStateChanged: NULL");
                        getUserDataFromFirebase(user.getUid());
                    }

                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };
    }

    private void getUserDataFromFirebase(String userID) {
        Log.d(TAG, "getUserDataFromFirebase: " + userID);
        myRef.child(userID)
                .addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserModel userModel = dataSnapshot.getValue(UserModel.class);
                Log.d(TAG, "onDataChange: " + userModel.getIsTeacher());
                addUserDataToSharedPrefrences(userModel);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "onCancelled: ", databaseError.toException());
            }
        });
    }

    @Override
    public void onStart() {
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    public void onButtonClick(String mail, String password, String name, String phone, String age, boolean isLogin) {
        if (!((Activity) mContext).isFinishing()) // So it won't crash if the activity is already finishing
            showProgressDialog(mContext, "Logging in...");
        if (!isLogin) // if he is logging in
            createUserWithEmailAndPassword(mail, password, name, phone, age);
        else // if he is signing up
            loginUserWithEmailAndPassword(mail, password);
    }

    private void createUserWithEmailAndPassword(final String email, final String password, final String name, final String phone, final String age) {
        if (isValidRegister(email, password, name, phone, age))
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());
                            // If sign in fails, display a message to the user. If sign up succeeds
                            // the auth state listener will be notified and logic to handle the
                            // signed in user can be handled in the listener.
                            if (!task.isSuccessful()) {
                                String exception = task.getException().getMessage();
                                Toast.makeText(mView.getContext(), exception,
                                        Toast.LENGTH_LONG).show();
                                Log.d(TAG, "onComplete: " + exception);
                            } else {
                                Toast.makeText(mView.getContext(), "Welcome learner",
                                        Toast.LENGTH_LONG).show();
                                addUserToFirebaseDataBase(email, password, name, phone, age);
                                sendVerificationEmail();
                            }
                            hideProgressDialog();
                        }
                    });
        else
            hideProgressDialog();
    }

    public void sendVerificationEmail() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            user.sendEmailVerification()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(mView.getContext(), "Registered successfully. " +
                                        "Verification email sent", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }

    }
    private boolean isValidRegister(String email, String password, String name, String phone, String age) {
        if (isEmpty(email)) {
            Toast.makeText(mView.getContext(), "Email is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(isEmpty(name)) {
            Toast.makeText(mView.getContext(), "Name is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(isEmpty(phone)) {
            Toast.makeText(mView.getContext(), "Phone number is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(isEmpty(age)) {
            Toast.makeText(mView.getContext(), "Age is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(isEmpty(password)) {
            Toast.makeText(mView.getContext(), "Password is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void loginUserWithEmailAndPassword(String email, String password) {
        if (isValidLogin(email, password))
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                            // If sign in fails, display a message to the user. If sign in succeeds
                            // the auth state listener will be notified and logic to handle the
                            // signed in user can be handled in the listener.
                            if (!task.isSuccessful()) {
                                Log.w(TAG, "signInWithEmail:failed", task.getException());
                                Toast.makeText(mView.getContext(), task.getException().getMessage(),
                                        Toast.LENGTH_LONG).show();
                            }
                            else {
                                // TODO: Add intent to the new activity
                            }
                            hideProgressDialog();
                        }
                    });
        else
            hideProgressDialog();
    }

    private boolean isValidLogin(String email, String password) {
        if (isEmpty(email)) {
            Toast.makeText(mView.getContext(), "Email is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(isEmpty(password)) {
            Toast.makeText(mView.getContext(), "Password is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void addUserToFirebaseDataBase(String email, String password, String name, String phone, String age) {
        UserModel userModel = new UserModel(email, name, Integer.parseInt(phone), Integer.parseInt(age), isTeacher(email));
        Log.d(TAG, "addUserToFirebaseDataBase: " + userModel.getName());
        myRef.child(user.getUid()).setValue(userModel);
    }

    private void addUserDataToSharedPrefrences(UserModel userModel) {
        SharedPreferences.Editor editor = mView.getContext().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        String jsonUserModel = new Gson().toJson(userModel);
        editor.putString(USERSHAREDPREFS, jsonUserModel);
        editor.commit();
        mView.getContext().startActivity(new Intent(mView.getContext(), Home.class));
    }

    private boolean isTeacher(String email) {
        return (email.contains(TEACHEREMAIL));
    }
}
