package com.example.note_managerment.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.note_managerment.model.Note;
import com.example.note_managerment.model.Status;

import java.util.List;
@Dao
public interface NoteDao {
    @Query("SELECT * FROM note")
    List<Note> getAllNote();

    @Insert
    void insertNote(Note... note_rooms);

    @Update
    void updateNote(Note... notes);

    @Delete
    void deleteNote(Note... note_rooms);
}
