package com.example.note_managerment.adapter;

import android.view.View;

public interface ICategoryAdapter {

    void editCategory(View view, int position);

    void deleteCategory(View view,int position);

//    void initVariable(View view);
//
//    boolean isEmpty(String textBox);
//
//    void displayItem(View view, List<Category> category);
//
//    void handleInsertEvent(String message, View view);
}
