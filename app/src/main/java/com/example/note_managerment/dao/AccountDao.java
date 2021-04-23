package com.example.note_managerment.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.note_managerment.model.Account;


@Dao
public interface AccountDao {
    @Insert
    void insertUser(Account... user);

    @Update
    void updateUser(Account... user);

    @Delete
    void deleteUser(Account... user);

    @Query("SELECT * FROM account WHERE email=(:email) AND password=(:password) ")
    Account getUser(String email, String password);

    @Query("SELECT * FROM account WHERE email=(:email)")
    Account checkUser(String email);
}
