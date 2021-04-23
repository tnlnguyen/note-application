package com.example.note_managerment.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.note_managerment.dao.CategoryDao;
import com.example.note_managerment.model.Category;


@Database(entities = {Category.class}, version = 1)
public abstract class CategoryDatabase extends RoomDatabase {
    public static final String DB_NAME = "my_db";

    public abstract CategoryDao getCategoryDao();
}