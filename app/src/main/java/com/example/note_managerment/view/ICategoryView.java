package com.example.note_managerment.view;


import android.view.View;

import androidx.fragment.app.FragmentActivity;

import com.example.note_managerment.model.Category;

import java.util.List;

public interface ICategoryView {
    void insertCategory(View view);

    void initVariable(View view);

    boolean isEmpty(String textBox);

    void displayItem(View view, List<Category> category);

    void handleInsertEvent(String message, View view);

    FragmentActivity getFragmentActivity();

}
