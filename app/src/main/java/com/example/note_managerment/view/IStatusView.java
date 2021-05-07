package com.example.note_managerment.view;

import android.view.View;

import androidx.fragment.app.FragmentActivity;

import com.example.note_managerment.model.Status;

import java.util.List;

public interface IStatusView {
    void insertStatus(View view);

    void initVariable(View view);

    boolean isEmpty(String textBox);

    void displayItemStatus(View view, List<Status> status);

    void handleInsertEvent(String message, View view);

    FragmentActivity getFragmentActivity();
}
