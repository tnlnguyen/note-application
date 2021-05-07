package com.example.note_managerment.view;

public interface ILoginView {
    void initVariable();

    void handleEvent();

    void handlePreferences(Integer Id);

    void handleInsertEvent(String message);
}
