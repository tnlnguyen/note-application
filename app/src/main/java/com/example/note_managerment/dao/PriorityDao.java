package com.example.note_managerment.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.note_managerment.model.Priority;
import com.example.note_managerment.model.Status;

import java.util.List;

@Dao
public interface PriorityDao {

    @Query("SELECT * FROM priority")
    List<Priority> getAllPriority();

    @Insert
    void insertPriority(Priority... priority_rooms);

    @Update
    void updatePriority(Priority... priorities);

    @Delete
    void deletePriority(Priority... priority_rooms);

}