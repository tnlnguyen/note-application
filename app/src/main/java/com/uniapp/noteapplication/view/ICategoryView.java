package com.uniapp.noteapplication.view;

import android.os.Parcelable;
import android.view.View;

import com.uniapp.noteapplication.model.Category;

import java.util.List;

public interface ICategoryView {
    void insertCategory(View view);

    void initVariable(View view);

    void displayItem(View view, List<Category> category);

    void handleInsertEvent(View view,String message);

    void processDialogEnable(View view);

    void processDialogDisable();
}
