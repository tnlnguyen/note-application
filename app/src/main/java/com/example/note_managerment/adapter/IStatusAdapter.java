package com.example.note_managerment.adapter;

import android.view.View;

import com.example.note_managerment.model.Status;

import java.util.List;

public interface IStatusAdapter {
    void editStatus(View view, int position);

    void deleteStatus(View view,int position);

//    void initVariable(View view);
//
//    boolean isEmpty(String textBox);
//
//    void displayItem(View view, List<Status> status);
//
//    void handleInsertEvent(String message, View view);
}
