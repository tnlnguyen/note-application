package com.uniapp.noteapplication.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.uniapp.noteapplication.dao.CategoryDao;
import com.uniapp.noteapplication.model.Category;

@Database(entities = {Category.class}, version = 1)
public abstract class CategoryDatabase extends RoomDatabase {
    public static final String DB_NAME = "my_db";

    public static CategoryDao getCategoryDao() {
        return null;
    }

}