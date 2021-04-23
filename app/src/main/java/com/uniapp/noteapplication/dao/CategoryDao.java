package com.uniapp.noteapplication.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.uniapp.noteapplication.model.Category;

import java.util.List;

@Dao
public interface CategoryDao {

    @Query("SELECT * FROM category")
    List<Category> getAllCategory();

    @Insert
    void insertCategory(Category... category_rooms);

    @Update
    void updateCategory(Category... category_rooms);

    @Delete
    void deleteCategory(Category... category_rooms);

}
