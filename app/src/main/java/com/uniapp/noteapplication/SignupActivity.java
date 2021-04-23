package com.uniapp.noteapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.uniapp.noteapplication.controller.AccountController;
import com.uniapp.noteapplication.controller.IAccountController;
import com.uniapp.noteapplication.view.ISignupView;

import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity implements ISignupView {

    EditText textEmail, textPassword, textConfirmPassword;
    Button btnSignUp, btnSignIn;

    IAccountController accountController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        accountController = new AccountController(null, this);

        initVariable();
        handleEvent();
    }



    /* Initialization variables */
    @Override
    public void initVariable() {
        textEmail = findViewById(R.id.text_email);
        textPassword = findViewById(R.id.text_pass);
        textConfirmPassword = findViewById(R.id.text_confirm_pass);
        btnSignIn = findViewById(R.id.btn_signin);
        btnSignUp = findViewById(R.id.btn_signup);
    }

    /* Handling Events */
    @Override
    public void handleSignUp() {
        startActivity(new Intent(SignupActivity.this, LoginActivity.class));
        finish();
    }

    @Override
    public void handleEvent() {
        btnSignIn.setOnClickListener(v -> {
            Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });

        btnSignUp.setOnClickListener(v -> {
            Map<String, Object> params = new HashMap<>();
            params.put("email", textEmail.getText().toString());
            params.put("password", textPassword.getText().toString());
            params.put("confirm", textConfirmPassword.getText().toString());

            accountController.signUp(params);
        });
    }

    @Override
    public void handleInsertEvent(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}