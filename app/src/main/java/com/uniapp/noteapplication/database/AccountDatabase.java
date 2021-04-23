package com.uniapp.noteapplication.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.uniapp.noteapplication.dao.AccountDao;
import com.uniapp.noteapplication.model.Account;

@Database(entities = {Account.class}, version = 1)
public abstract class AccountDatabase extends RoomDatabase {

    public static final String DB_NAME = "user_db";

    public abstract AccountDao getAccountDao();

}