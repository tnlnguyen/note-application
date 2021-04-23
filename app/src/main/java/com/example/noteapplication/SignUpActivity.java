package com.example.noteapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.noteapplication.R;
import com.example.noteapplication.UserDAO;
import com.example.noteapplication.UserDataBase;
import com.example.noteapplication.UserEntity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {

    EditText text_email, text_pass, text_confirm_pass;
    Button btn_signup,btn_signin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        text_email = findViewById(R.id.text_email);
        text_pass = findViewById(R.id.text_pass);
        text_confirm_pass = findViewById(R.id.text_confirm_pass);

        btn_signin = findViewById(R.id.btn_signin);
        btn_signup = findViewById(R.id.btn_signup);

        UserDataBase userDataBase= Room.databaseBuilder(this,UserDataBase.class,"user_db")
                .allowMainThreadQueries()
                .build();

        btn_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btn_signup.setOnClickListener(v -> {

            UserDAO userDAO =userDataBase.getUserDAO();
            UserEntity user=new UserEntity();
            user.setEmail(text_email.getText().toString());
            user.setPassword(text_pass.getText().toString());

            if (userDAO.checkUser(user.getEmail()) ==null) {
                if (!(text_pass.getText().toString().equals(text_confirm_pass.getText().toString()))) {
                    text_pass.setError("Password does not match");
                    text_confirm_pass.setError("Password does not match");
                } else if (!isEmpty()) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            //insertUser
                            if (user != null) {
                                userDAO.insertUser(user);
                                Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                                finish();
                            }

                        }
                    }, 1000);
                } else {
                    Toast.makeText(this, "Empty Fields", Toast.LENGTH_LONG).show();
                }
            }
            else
            {
                Toast.makeText(this, "Email Exist!", Toast.LENGTH_LONG).show();
            }
        });
    }
    private  boolean isEmpty()
    {
        if(TextUtils.isEmpty(text_email.getText().toString())
                ||TextUtils.isEmpty(text_pass.getText().toString())
                ||TextUtils.isEmpty(text_confirm_pass.getText().toString()))
            return true;
        else
            return  false;
    }
}