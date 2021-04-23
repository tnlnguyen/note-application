package com.uniapp.noteapplication.controller;

import android.content.Context;
import android.text.TextUtils;

import androidx.core.content.ContextCompat;
import androidx.room.Room;

import com.uniapp.noteapplication.dao.AccountDao;
import com.uniapp.noteapplication.database.AccountDatabase;
import com.uniapp.noteapplication.model.Account;
import com.uniapp.noteapplication.view.ILoginView;
import com.uniapp.noteapplication.view.ISignupView;

import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AccountController implements IAccountController {
    ILoginView loginView;
    ISignupView signupView;

    private AccountDatabase accountDatabase;

    public AccountController(ILoginView loginView, ISignupView signupView) {
        this.loginView = loginView;
        this.signupView = signupView;

        if (loginView != null) {
            accountDatabase = Room.databaseBuilder((Context) loginView, AccountDatabase.class, AccountDatabase.DB_NAME).build();
        } else {
            accountDatabase = Room.databaseBuilder((Context) signupView, AccountDatabase.class, AccountDatabase.DB_NAME).build();
        }
    }

    @Override
    public void login(Map<String, Object> params) {
        String email = (String) params.get("email");
        String password = (String) params.get("password");

        if(!isEmpty(email, password)) {
            Executor myExecutor = Executors.newSingleThreadExecutor();
            myExecutor.execute(() -> {
                AccountDao accountDao =accountDatabase.getAccountDao();
                if(accountDao.getUser(email,password) != null) {
                    loginView.handlePreferences();
                } else {
                    ContextCompat.getMainExecutor((Context) loginView).execute(()  -> {
                        loginView.handleInsertEvent("Account is not found!");
                    });
                }
            });
        } else {
            loginView.handleInsertEvent("Please fill all empty fields");
        }
    }

    @Override
    public void signUp(Map<String, Object> params) {
        String email = (String) params.get("email");
        String password = (String) params.get("password");
        String confirm = (String) params.get("confirm");

        if (!isSignUpEmpty(email, password, confirm)) {
            if (!password.equals(confirm)) {
                signupView.handleInsertEvent("Password does not match!");
                return;
            }

            Executor myExecutor = Executors.newSingleThreadExecutor();
            myExecutor.execute(() -> {
                AccountDao accountDao = accountDatabase.getAccountDao();
                if (accountDao.checkUser(email) != null) {
                    ContextCompat.getMainExecutor((Context) signupView).execute(()  -> {
                        ContextCompat.getMainExecutor((Context) signupView).execute(()  -> {
                            signupView.handleInsertEvent("Email has already existed");
                        });
                    });
                    return;
                }
                Account account = new Account();
                account.setEmail(email);
                account.setPassword(password);

                accountDao.insertUser(account);

                signupView.handleSignUp();
                ContextCompat.getMainExecutor((Context) signupView).execute(()  -> {
                    signupView.handleInsertEvent("Successfully!");
                });
            });
        } else {
            signupView.handleInsertEvent("Please fill all empty fields!");
        }
    }



    @Override
    public boolean isEmpty(String email, String password) {
        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password))
            return true;
        else
            return false;
    }

    @Override
    public boolean isSignUpEmpty(String email, String password, String confirm) {
        if(TextUtils.isEmpty(email)
                ||TextUtils.isEmpty(password)
                ||TextUtils.isEmpty(confirm))
            return true;
        else
            return  false;
    }
}
