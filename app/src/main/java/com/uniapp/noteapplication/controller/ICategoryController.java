package com.uniapp.noteapplication.controller;

import android.view.View;

import com.uniapp.noteapplication.model.Category;

import java.util.List;
import java.util.Map;

public interface ICategoryController {
    void insertCategory(Map<String, Object> params);

    void getListItem();

    boolean isEmpty(String textBox);
}
