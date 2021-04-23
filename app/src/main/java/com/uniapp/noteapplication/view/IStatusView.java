package com.uniapp.noteapplication.view;

import android.view.View;

import com.uniapp.noteapplication.model.Status;

import java.util.List;

public interface IStatusView {
    void editStatus(View view,int position);

    void deleteStatus(View view,int position);

    void initVariable(View view);

    boolean isEmpty(String textBox);

    void displayItem(View view, List<Status> status);

    void handleInsertEvent(String message, View view);

    void insertStatus(View view);
}
