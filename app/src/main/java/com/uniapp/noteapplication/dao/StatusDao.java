package com.uniapp.noteapplication.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.uniapp.noteapplication.model.Status;

import java.util.List;

@Dao
public interface StatusDao {

    @Query("SELECT * FROM status")
    List<Status> getAllStatus();

    @Insert
    void insertStatus(Status... status);

    @Update
    void updateStatus(Status... status);

    @Delete
    void deleteStatus(Status... status);

}