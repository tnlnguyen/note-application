package com.uniapp.noteapplication.controller;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.View;

import androidx.core.content.ContextCompat;
import androidx.room.Room;

import com.uniapp.noteapplication.adapter.CategoryAdapter;
import com.uniapp.noteapplication.dao.CategoryDao;
import com.uniapp.noteapplication.database.CategoryDatabase;
import com.uniapp.noteapplication.model.Category;
import com.uniapp.noteapplication.view.ICategoryView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
        this.view = view;
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

            if (!isEmpty(txtCategory)) {
                Executor myExecutor = Executors.newSingleThreadExecutor();
                myExecutor.execute(() -> {
                    categoryDao.insertCategory(category);
                    categoryView.processDialogDisable();
                    ContextCompat.getMainExecutor((Context) categoryView).execute(()  -> {
                        categoryView.handleInsertEvent(view,"Successfully!");
                    });
                });
            } else {
                categoryView.handleInsertEvent(view,"Please fill all empty fields!");
            }
        } catch (Exception e) {
            categoryView.handleInsertEvent(view, e.getMessage());
        }
    }

    @Override
    public void getListItem() {
        new getListItemTask().execute();
    }

    @Override
    public boolean isEmpty(String textBox) {
        if(TextUtils.isEmpty(textBox))
            return true;
        else
            return false;
    }

    private class getListItemTask extends AsyncTask<Void, List<Category>, List<Category>> {
        @Override
        public List<Category> doInBackground(Void... maps) {
            CategoryDao categoryDao = categoryDatabase.getCategoryDao();
            return categoryDao.getAllCategory();
        }

        @Override
        protected void onPostExecute(List<Category> categoryList) {
            super.onPostExecute(categoryList);
            categoryView.displayItem(view, categoryList);
        }
    }

}
