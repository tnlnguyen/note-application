package com.uniapp.noteapplication.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.uniapp.noteapplication.dao.StatusDao;
import com.uniapp.noteapplication.model.Status;

@Database(entities = {Status.class}, version = 1)
public abstract class StatusDatabase extends RoomDatabase {
    public static final String DB_NAME = "my_db";

    public abstract StatusDao getStatusDao();

}