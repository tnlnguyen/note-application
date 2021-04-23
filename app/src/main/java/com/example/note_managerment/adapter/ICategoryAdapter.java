package com.example.note_managerment.adapter;

import android.view.View;

import com.example.note_managerment.model.Category;
import com.example.note_managerment.model.Status;

import java.util.List;

public interface ICategoryAdapter {

    void editCategory(View view, int position);

    void deleteCategory(View view,int position);

    void initVariable(View view);

    boolean isEmpty(String textBox);

    void displayItem(View view, List<Category> category);

    void handleInsertEvent(String message, View view);
}
