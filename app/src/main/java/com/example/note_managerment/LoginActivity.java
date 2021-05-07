package com.example.note_managerment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.note_managerment.MainActivity;
import com.example.note_managerment.controller.AccountController;
import com.example.note_managerment.controller.IAccountController;
import com.example.note_managerment.view.ILoginView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements ILoginView {

    SharedPreferences sharedPreferences;
    EditText textEmail, textPassword;
    Button btnSignIn, btnExit;
    FloatingActionButton btnSignUp;
    CheckBox checkBoxRemember;

    IAccountController accountController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sharedPreferences = getSharedPreferences("DataLogin", MODE_PRIVATE);
        accountController = new AccountController(this, null, null);

        initVariable();
        handleEvent();
    }

    /* Implementation functions */
    @Override
    public void handleEvent() {
        textEmail.setText(sharedPreferences.getString("email",""));
        textPassword.setText(sharedPreferences.getString("password",""));
        checkBoxRemember.setChecked(sharedPreferences.getBoolean("checked",false));

        btnSignIn.setOnClickListener(v -> {
            Map<String, Object> params = new HashMap<>();
            params.put("email", textEmail.getText().toString());
            params.put("password", textPassword.getText().toString());

            accountController.login(params);
        });

        btnSignUp.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, SignupActivity.class));
            finish();
        });
    }

    /* Initialization functions */
    @Override
    public void initVariable() {
        textEmail = (EditText) findViewById(R.id.text_email);
        textPassword = (EditText) findViewById(R.id.text_pass);
        btnSignIn = (Button) findViewById(R.id.btn_signin);
        btnExit = (Button) findViewById(R.id.btn_exit);
        btnSignUp = (FloatingActionButton) findViewById(R.id.floating_btn_signup);
        checkBoxRemember = (CheckBox) findViewById(R.id.cb_remember);
    }

    @Override
    public void handlePreferences() {
        if(checkBoxRemember.isChecked()) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("email", textEmail.getText().toString());
            editor.putString("password",textPassword.getText().toString());
            editor.putBoolean("checked",true);
            editor.apply();
        }
        else {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove("email");
            editor.remove("password");
            editor.remove("checked");
            editor.apply();
        }

        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        finish();
    }

    @Override
    public void handleInsertEvent(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}