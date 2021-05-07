package com.example.note_managerment.adapter;

import android.view.View;

import com.example.note_managerment.model.Priority;

import java.util.List;

public interface IPriorityAdapter {
    void editPriority(View view, int position);

    void deletePriority(View view, int position);

}
