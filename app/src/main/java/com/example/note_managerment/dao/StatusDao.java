package com.example.note_managerment.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.note_managerment.model.Status;

import java.util.List;

@Dao
public interface StatusDao {

    @Query("SELECT * FROM status")
    List<Status> getAllStatus();

    @Insert
    void insertStatus(Status... status_rooms);

    @Update
    void updateStatus(Status... statuses);

    @Delete
    void deleteStatus(Status status_rooms);

}