package com.learnrush.presenter;

public interface RegisterInterFace{
    void onCreate();
    void onButtonClick(String name, String mail, String password, String phone, String age, boolean isLogin);
    void onStart();
    void onStop();
}
