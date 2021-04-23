package com.example.noteapplication;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


@Dao
public interface UserDAO {

    @Query("SELECT * FROM users WHERE email=(:email) AND password=(:password) ")
    UserEntity getUser(String email, String password);

    @Query("SELECT * FROM users WHERE email=(:email)")
    UserEntity checkUser(String email);

    @Insert
    void insertUser(UserEntity... user);

    @Update
    void updateUser(UserEntity... user);

    @Delete
    void deleteUser(UserEntity... user);

}
