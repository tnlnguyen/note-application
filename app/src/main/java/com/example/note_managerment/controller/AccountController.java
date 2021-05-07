package com.example.note_managerment.controller;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import androidx.core.content.ContextCompat;
import androidx.room.Room;

import com.example.note_managerment.dao.AccountDao;
import com.example.note_managerment.dao.CategoryDao;
import com.example.note_managerment.database.CategoryDatabase;
import com.example.note_managerment.model.Account;
import com.example.note_managerment.model.Category;
import com.example.note_managerment.view.IChangePasswordView;
import com.example.note_managerment.view.ILoginView;
import com.example.note_managerment.view.ISignupView;

import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AccountController implements IAccountController {
    ILoginView loginView;
    ISignupView signupView;
    IChangePasswordView changePasswordView;

    private CategoryDatabase accountDatabase;

    public AccountController(ILoginView loginView, ISignupView signupView, IChangePasswordView changePasswordView) {
        this.loginView = loginView;
        this.signupView = signupView;
        this.changePasswordView = changePasswordView;

        if (loginView != null) {
            accountDatabase = Room.databaseBuilder((Context) loginView, CategoryDatabase.class, CategoryDatabase.DB_NAME)            .fallbackToDestructiveMigration()
                    .build();
        } else {
            accountDatabase = Room.databaseBuilder((Context) signupView, CategoryDatabase.class, CategoryDatabase.DB_NAME)            .fallbackToDestructiveMigration()
                    .build();
        }
    }

    @Override
    public void login(Map<String, Object> params) {
        String email = (String) params.get("email");
        String password = (String) params.get("password");

        if(!isEmpty(email, password)) {
            Executor myExecutor = Executors.newSingleThreadExecutor();
            myExecutor.execute(() -> {
                AccountDao accountDao = (AccountDao) accountDatabase.getAccountDao();
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
    public void changePassword(Map<String, Object> params) {
        String current = (String) params.get("current");
        String newPassword = (String) params.get("new");
        String confirm = (String) params.get("confirm");

        if(isChangePassEmpty(current, newPassword, confirm))
        {
            changePasswordView.handleEvent("Please fill all empty fields!", (View) changePasswordView);
        } else if(isNewPasstrue(newPassword, confirm)){
            changePasswordView.handleEvent("Password doesn't match!", (View) changePasswordView);
        }

        Executor myExecutor = Executors.newSingleThreadExecutor();
        myExecutor.execute(() -> {
            AccountDao accountDao = accountDatabase.getAccountDao();

            Account account = new Account();
            account.setEmail((String) params.get("email"));
            account.setPassword(newPassword);

            accountDao.updateUser(account);

            changePasswordView.savePreferences(params);

            ContextCompat.getMainExecutor((Context) changePasswordView).execute(()  -> {
                changePasswordView.handleEvent("Successfully!", (View) changePasswordView);
            });
        });
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
    @Override
    public boolean isChangePassEmpty(String password, String newPassword, String confirm) {
        if(TextUtils.isEmpty(newPassword)
                ||TextUtils.isEmpty(password)
                ||TextUtils.isEmpty(confirm))
            return true;
        else
            return  false;
    }

    @Override
    public boolean isNewPasstrue(String newPassword, String confirm) {
        if(newPassword.equals(confirm))
            return true;
        else
            return  false;
    }

}