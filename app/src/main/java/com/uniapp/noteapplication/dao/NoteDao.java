package com.uniapp.noteapplication.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.uniapp.noteapplication.model.Category;
import com.uniapp.noteapplication.model.Note;

import java.util.List;

@Dao
public interface NoteDao {

    @Query("SELECT * FROM note")
    List<Note> getAllNote();

    @Insert
    void insertNote(Note... note_rooms);

    @Update
    void updateNote(Note... note_rooms);

    @Delete
    void deleteNote(Note... note_rooms);

}