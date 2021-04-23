package com.example.noteapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.noteapplication.R;
import com.example.noteapplication.UserDAO;
import com.example.noteapplication.UserDataBase;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class LoginActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;


    EditText text_email, text_pass;
    Button btn_signin, btn_exit;
    FloatingActionButton floating_btn_signup;
    CheckBox cb_remember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sharedPreferences = getSharedPreferences("DataLogin", MODE_PRIVATE);

        text_email = (EditText) findViewById(R.id.text_email);
        text_pass = (EditText) findViewById(R.id.text_pass);
        btn_signin = (Button) findViewById(R.id.btn_signin);
        btn_exit = (Button) findViewById(R.id.btn_exit);
        floating_btn_signup = (FloatingActionButton) findViewById(R.id.floating_btn_signup);
        cb_remember = (CheckBox) findViewById(R.id.cb_remember);

        text_email.setText(sharedPreferences.getString("email",""));
        text_pass.setText(sharedPreferences.getString("Pass",""));
        cb_remember.setChecked(sharedPreferences.getBoolean("checked",false));

        UserDataBase dataBase= Room.databaseBuilder(this,UserDataBase.class,"user_db")
                .allowMainThreadQueries()
                .build();

        btn_signin.setOnClickListener(v -> {

            String userIdText = text_email.getText().toString();
            String passwordText = text_pass.getText().toString();
            if(!isEmpty())
            {

                    UserDAO userDAO=dataBase.getUserDAO();
                    if(userDAO.getUser(userIdText,passwordText)!=null)
                    {
                        startActivity(new Intent(LoginActivity.this,Dashboard.class));
                        finish();

                        if(cb_remember.isChecked()) {
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("email", userIdText);
                            editor.putString("Pass",passwordText);
                            editor.putBoolean("checked",true);
                            editor.commit();
                        }
                        else {
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.remove("email");
                            editor.remove("Pass");
                            editor.remove("checked");
                            editor.commit();
                        }
                    }
                    else
                        Toast.makeText(getApplicationContext(),"Empty Field",Toast.LENGTH_LONG).show();
            }
            else
                Toast.makeText(this,"Login false!",Toast.LENGTH_LONG).show();
        });

        floating_btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private boolean isEmpty() {
        if(TextUtils.isEmpty(text_email.getText().toString())||TextUtils.isEmpty(text_pass.getText().toString()))
            return true;
        else
            return false;
    }
}