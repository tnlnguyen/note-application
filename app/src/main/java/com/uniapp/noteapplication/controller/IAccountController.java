package com.uniapp.noteapplication.controller;

import java.util.Map;

public interface IAccountController {
    void login(Map<String, Object> params);

    void signUp(Map<String, Object> params);

    boolean isEmpty(String email, String password);

    boolean isSignUpEmpty(String email, String password, String confirm);
}
