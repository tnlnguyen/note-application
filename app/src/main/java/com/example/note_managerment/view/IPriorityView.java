package com.example.note_managerment.view;

import android.view.View;

import androidx.fragment.app.FragmentActivity;

import com.example.note_managerment.model.Priority;

import java.util.List;

public interface IPriorityView {
    void insertPriority(View view);

    void initVariable(View view);

    boolean isEmpty(String textBox);

    void displayItem(View view, List<Priority> priorityList);

    void handleInsertEvent(String message, View view);

    FragmentActivity getFragmentActivity();
}
