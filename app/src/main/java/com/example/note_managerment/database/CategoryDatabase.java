package com.example.note_managerment.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.note_managerment.dao.CategoryDao;
import com.example.note_managerment.dao.StatusDao;
import com.example.note_managerment.model.Category;
import com.example.note_managerment.model.Status;


@Database(entities = {Category.class, Status.class}, version = 2)
public abstract class CategoryDatabase extends RoomDatabase {
    public static final String DB_NAME = "my_db";

    public abstract CategoryDao getCategoryDao();
    public abstract StatusDao getStatusDao();

}