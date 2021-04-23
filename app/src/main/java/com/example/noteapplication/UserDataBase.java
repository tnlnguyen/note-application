package com.example.noteapplication;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.example.noteapplication.UserDAO;
import com.example.noteapplication.UserEntity;

@Database(entities = {UserEntity.class}, version = 1)
public abstract class UserDataBase extends RoomDatabase {

    //  public static final String DB_NAME = "user_db";

    public abstract UserDAO getUserDAO();

}
