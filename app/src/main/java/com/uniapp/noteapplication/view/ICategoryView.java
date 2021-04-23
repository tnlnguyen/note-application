package com.uniapp.noteapplication.view;

import android.os.Parcelable;

import com.uniapp.noteapplication.model.Category;

import java.util.List;

public interface ICategoryView {
    void insertCategory();

    void initVariable();

    void displayItem( List<Category> category);

    void handleInsertEvent(String message);

    void processDialogEnable();

    void processDialogDisable();
}
