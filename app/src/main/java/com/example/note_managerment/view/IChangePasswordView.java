package com.example.note_managerment.view;

import android.view.View;

import java.util.Map;

public interface IChangePasswordView {
    void changePassWord();
    void handleEvent(String message, View view);
    void savePreferences(Map<String, Object> params);
}