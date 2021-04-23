package com.example.note_managerment.view;


import com.example.note_managerment.model.Category;

import java.util.List;

public interface ICategoryView {
    void insertCategory();

    void initVariable();

    boolean isEmpty(String textBox);

    void displayItem( List<Category> category);

    void handleInsertEvent(String message);
}
