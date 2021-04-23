package com.uniapp.noteapplication.controller;

import android.content.Context;

import androidx.room.Room;

import com.uniapp.noteapplication.dao.CategoryDao;
import com.uniapp.noteapplication.database.CategoryDatabase;
import com.uniapp.noteapplication.model.Category;
import com.uniapp.noteapplication.view.ICategoryView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

public class CategoryController implements ICategoryController {
    ICategoryView categoryView;
    private CategoryDatabase categoryDatabase;
    String currentDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault()).format(new Date());


    public CategoryController(ICategoryView verificationView) {
        this.categoryView = verificationView;
        categoryDatabase = Room.databaseBuilder((Context) verificationView, CategoryDatabase.class, CategoryDatabase.DB_NAME).build();
    }

    @Override
    public void insertCategory(Map<String, Object> params) {
        try {
            CategoryDao categoryDao = categoryDatabase.getCategoryDao();
            Category category = new Category();
            String txtCategory = (String) params.get("category");
            category.setName(txtCategory);
            category.setDate(currentDate);

            if (!categoryView.isEmpty(txtCategory)) {
                categoryDao.insertCategory(category);
                categoryView.handleInsertEvent("Successfully!");
            } else {
                categoryView.handleInsertEvent("Please fill all empty fields!");
            }
        } catch (Exception e) {
            categoryView.handleInsertEvent(e.getMessage());
        }
    }

}
