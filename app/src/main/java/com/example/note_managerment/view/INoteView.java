package com.example.note_managerment.view;

import android.view.View;

import com.example.note_managerment.model.Note;

import java.util.List;

public interface INoteView {

    void insertNote(View view);

    void initVariable(View view);

    boolean isEmpty(String textBox);

    void displayItemNote(View view, List<Note> note);

    void handleInsertEvent(String message, View view);
}
