package com.example.note_managerment.controller;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;

import androidx.room.Room;


import com.example.note_managerment.dao.CategoryDao;
import com.example.note_managerment.dao.StatusDao;
import com.example.note_managerment.database.CategoryDatabase;
import com.example.note_managerment.model.Category;
import com.example.note_managerment.model.Status;
import com.example.note_managerment.view.ICategoryView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class CategoryController implements ICategoryController {
    ICategoryView categoryView;
    View view;
    private CategoryDatabase categoryDatabase;
    String currentDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault()).format(new Date());


    public CategoryController(ICategoryView verificationView, View view) {
        this.categoryView = verificationView;
        this.view=view;
        categoryDatabase = Room.databaseBuilder(view.getContext(), CategoryDatabase.class, CategoryDatabase.DB_NAME).build();
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
                Executor myExecutor = Executors.newSingleThreadExecutor();
                myExecutor.execute(() -> {
                    categoryDao.insertCategory(category);

                });
            } else {
                categoryView.handleInsertEvent("Please fill all empty fields!",view);
            }
        } catch (Exception e) {
            categoryView.handleInsertEvent(e.getMessage(), view);
        }
    }

    @Override
    public void editCategory(Map<String, Object> params) {
        try {
            CategoryDao categoryDao = categoryDatabase.getCategoryDao();
            Category category = (Category) params.get("category");

            if (!categoryView.isEmpty(category.getName())) {
                Executor myExecutor = Executors.newSingleThreadExecutor();
                myExecutor.execute(() -> {
                    categoryDao.updateCategory(category);

                });
            } else {
                categoryView.handleInsertEvent("Please fill all empty fields!",view);
            }
        } catch (Exception e) {
            categoryView.handleInsertEvent(e.getMessage(),view);
        }

    }

    @Override
    public void deleteCategory(Map<String, Object> params) {
        try {
            CategoryDao categoryDao = categoryDatabase.getCategoryDao();
            Category category = (Category) params.get("category");

            if (!categoryView.isEmpty(category.getName())) {
                Executor myExecutor = Executors.newSingleThreadExecutor();
                myExecutor.execute(() -> {
                    categoryDao.deleteCategory(category);

                });
            } else {
                categoryView.handleInsertEvent("Please fill all empty fields!",view);
            }
        } catch (Exception e) {
            categoryView.handleInsertEvent(e.getMessage(),view);
        }
    }


    @Override
    public void getListItem() {
        new getListItemTask().execute();
    }

    private class getListItemTask extends AsyncTask<Void, List<Category>, List<Category>> {
        @Override
        public List<com.example.note_managerment.model.Category> doInBackground(Void... maps) {
            CategoryDao categoryDao = categoryDatabase.getCategoryDao();
            List<com.example.note_managerment.model.Category> categoryList = categoryDao.getAllCategory();
            return categoryList;
        }

        @Override
        protected void onPostExecute(List<com.example.note_managerment.model.Category> categoryList) {
            super.onPostExecute(categoryList);
            categoryView.displayItem(view,categoryList);
        }
    }
}