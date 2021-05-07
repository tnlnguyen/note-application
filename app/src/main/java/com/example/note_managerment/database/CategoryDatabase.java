package com.example.note_managerment.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.note_managerment.dao.AccountDao;
import com.example.note_managerment.dao.CategoryDao;
import com.example.note_managerment.dao.NoteDao;
import com.example.note_managerment.dao.PriorityDao;
import com.example.note_managerment.dao.StatusDao;
import com.example.note_managerment.model.Account;
import com.example.note_managerment.model.Category;
import com.example.note_managerment.model.Note;
import com.example.note_managerment.model.Priority;
import com.example.note_managerment.model.Status;


@Database(entities = {Category.class,  Status.class, Account.class, Note.class, Priority.class}, version = 5)
public abstract class CategoryDatabase extends RoomDatabase {
    public static final String DB_NAME = "my_db";
    public abstract CategoryDao getCategoryDao();
    public abstract StatusDao getStatusDao();
    public abstract AccountDao getAccountDao();
    public abstract NoteDao getNoteDao();
    public abstract PriorityDao getPriorityDao();

}