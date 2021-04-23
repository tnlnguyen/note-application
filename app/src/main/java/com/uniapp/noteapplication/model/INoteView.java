package com.uniapp.noteapplication.model;

import android.view.View;

import androidx.annotation.LayoutRes;

public interface INoteView {
    void openDialog(int gravity, View view, @LayoutRes int layoutResID, String title, boolean isAdd);
    void initView(View view);
    boolean isEmpty();
}
