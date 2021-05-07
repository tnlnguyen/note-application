package com.example.note_managerment.view;

import android.view.View;

public interface IChangePasswordView {
    void changePassWord(View view);

    void initVariable();

    void handleEvent();

    void handlePreferences();

    void handleInsertEvent(String message);
}
