package com.example.note_managerment.adapter;

import android.view.View;

import com.example.note_managerment.model.Priority;
import com.example.note_managerment.model.Status;

import java.util.List;

public interface IPriorityAdapter {
    void editPriority(View view, int position);

    void deletePriority(View view,int position);

    void initVariable(View view);

    boolean isEmpty(String textBox);

    void displayItem(View view, List<Priority> priorityList);

    void handleInsertEvent(String message, View view);
}
