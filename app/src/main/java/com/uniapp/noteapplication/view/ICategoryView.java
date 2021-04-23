package com.uniapp.noteapplication.view;

import android.os.Parcelable;

public interface ICategoryView {
    void openDialog();

    void initVariable();

    boolean isEmpty(String textBox);

    void displayItem();

    void handleInsertEvent(String message);
}
