package com.example.note_managerment.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.note_managerment.dao.StatusDao;
import com.example.note_managerment.model.Status;

@Database(entities = {Status.class}, version = 1)
public abstract class StatusDatabase extends RoomDatabase {
    public static final String DB_NAME = "my_db";

    public abstract StatusDao getStatusDao();

}