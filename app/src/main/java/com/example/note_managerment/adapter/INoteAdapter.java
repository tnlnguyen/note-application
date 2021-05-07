package com.example.note_managerment.adapter;

import android.view.View;

import com.example.note_managerment.model.Note;

import java.util.List;

public interface INoteAdapter {
    void editNote(View view, int position);

    void deleteNote(View view,int position);

    void initVariable(View view);

    boolean isEmpty(String textBox);

    void displayItem(View view, List<Note> status);

    void handleInsertEvent(String message, View view);
}
