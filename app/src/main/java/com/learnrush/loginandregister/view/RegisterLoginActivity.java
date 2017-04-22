package com.learnrush.loginandregister.view;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.learnrush.R;
import com.learnrush.loginandregister.presenter.IRegisterAndLoginPresenter;
import com.learnrush.loginandregister.presenter.RegisterAndLoginPresenter;

public class RegisterLoginActivity extends AppCompatActivity implements View.OnClickListener, IView {
    private String TAG = "RegisterLoginActivityLOG";

    private EditText emailEditText, passwordEditText, nameEditText, phoneEditText, ageEditText;
    private Button btn_register;
    private TextView switchSignupandLoginTextView;

    private String email, password, name, phone, age;

    IRegisterAndLoginPresenter mIRegisterAndLogin;

    private boolean isLogin = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_login);

        View view = findViewById(R.id.ll_signup_container);

        mIRegisterAndLogin = new RegisterAndLoginPresenter(view, this);
        mIRegisterAndLogin.onCreate();

        emailEditText = (EditText) findViewById(R.id.et_email);
        passwordEditText = (EditText) findViewById(R.id.et_password);
        nameEditText = (EditText) findViewById(R.id.et_name);
        phoneEditText = (EditText) findViewById(R.id.et_phone);
        ageEditText = (EditText) findViewById(R.id.et_age);

        btn_register = (Button) findViewById(R.id.btn_signup);
        btn_register.setOnClickListener(this);

        switchSignupandLoginTextView = (TextView) findViewById(R.id.tv_login_signup_switch);
    }


    @Override
    public void onStart() {
        super.onStart();
        mIRegisterAndLogin.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mIRegisterAndLogin.onStop();
        finish();
    }

    @Override
    public void onClick(View view) {
        email = emailEditText.getText().toString();
        password = passwordEditText.getText().toString();
        name = nameEditText.getText().toString();
        phone = phoneEditText.getText().toString();
        age = ageEditText.getText().toString();
        mIRegisterAndLogin.onButtonClick(email, password, name, phone, age, isLogin);
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

    private ProgressDialog mProgressDialog;

    @Override
    public void showProgressDialog(String message) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(message);
            mProgressDialog.setIndeterminate(true);
            mProgressDialog.setCancelable(false);
        }
        mProgressDialog.show();
    }
    @Override
    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }
}
