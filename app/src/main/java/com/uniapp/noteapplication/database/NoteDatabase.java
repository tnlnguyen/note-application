package com.uniapp.noteapplication.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.uniapp.noteapplication.dao.NoteDao;
import com.uniapp.noteapplication.model.Note;

@Database(entities = {Note.class}, version = 1)
public abstract class NoteDatabase extends RoomDatabase {
    public static final String DB_NAME = "my_db";

    public abstract NoteDao getNoteDao();

}