package com.learnrush;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.learnrush.presenter.RegisterInterFace;
import com.learnrush.presenter.SignupAndLoginPresenter;

public class RegisterLoginActivity extends AppCompatActivity implements View.OnClickListener {
    private String TAG = "SignUpLOG";

    private EditText emailEditText, passwordEditText, nameEditText, phoneEditText, ageEditText;
    private Button btn_register;
    private TextView switchSignupandLoginTextView;

    private String email, password, name, phone, age;

    RegisterInterFace mRegisterInterFace;
    SignupAndLoginPresenter mSignupPresenter;

    private boolean isLogin = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_login);

        View view = findViewById(R.id.ll_signup_container);

        emailEditText = (EditText) findViewById(R.id.et_email);
        passwordEditText = (EditText) findViewById(R.id.et_password);
        nameEditText = (EditText) findViewById(R.id.et_name);
        phoneEditText = (EditText) findViewById(R.id.et_phone);
        ageEditText = (EditText) findViewById(R.id.et_age);

        btn_register = (Button) findViewById(R.id.btn_signup);
        btn_register.setOnClickListener(this);

        switchSignupandLoginTextView = (TextView) findViewById(R.id.tv_login_signup_switch);

        mSignupPresenter = new SignupAndLoginPresenter(view, this);
        mSignupPresenter.onCreate();
        mRegisterInterFace = mSignupPresenter;
    }


    @Override
    public void onStart() {
        super.onStart();
        mRegisterInterFace.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mRegisterInterFace.onStop();
        finish();
    }

    @Override
    public void onClick(View view) {
        email = emailEditText.getText().toString();
        password = passwordEditText.getText().toString();
        name = nameEditText.getText().toString();
        phone = phoneEditText.getText().toString();
        age = ageEditText.getText().toString();
        mRegisterInterFace.onButtonClick(email, password, name, phone, age, isLogin);
    }

    public void tv_loginClick(View view) {
        isLogin = !isLogin;
        if (isLogin) {
            nameEditText.setVisibility(View.GONE);
            phoneEditText.setVisibility(View.GONE);
            ageEditText.setVisibility(View.GONE);
            switchSignupandLoginTextView.setText(R.string.register_message);
            btn_register.setText("LOGIN");
        } else {
            nameEditText.setVisibility(View.VISIBLE);
            phoneEditText.setVisibility(View.VISIBLE);
            ageEditText.setVisibility(View.VISIBLE);
            switchSignupandLoginTextView.setText(R.string.login_message);
            btn_register.setText("SIGNUP");
        }
    }


}
