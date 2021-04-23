package com.uniapp.noteapplication.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

@Entity(tableName = "note")
public class Note {

    @PrimaryKey(autoGenerate = true)
    Integer id;

    @ColumnInfo(name = "name")
    String name;

    @ColumnInfo(name = "category")
    String category;

    @ColumnInfo(name = "priority")
    String priority;

    @ColumnInfo(name = "status")
    String status;

    @ColumnInfo(name = "planDate")
    String planDate;

    @ColumnInfo(name = "createdDate")
    String createdDate;

    public Note() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPriority() {
        return priority;
    }

    public Note(String name, String category, String priority, String status, String planDate, String createdDate) {
        this.name = name;
        this.category = category;
        this.priority = priority;
        this.status = status;
        this.planDate = planDate;
        this.createdDate = createdDate;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPlanDate() {
        return planDate;
    }

    public void setPlanDate(String planDate) {
        this.planDate = planDate;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public ArrayList<Note> initList()
    {
        ArrayList<Note> lst_note = new ArrayList<Note>();
        lst_note.add(new Note("Name","Category","Priority","Done","2021-04-22 18:50:20","2021-04-22 18:50:20"));
        lst_note.add(new Note("Name","Category","Priority","Done","2021-04-22 18:50:20","2021-04-22 18:50:20"));
        lst_note.add(new Note("Name","Category","Priority","Done","2021-04-22 18:50:20","2021-04-22 18:50:20"));
        lst_note.add(new Note("Name","Category","Priority","Done","2021-04-22 18:50:20","2021-04-22 18:50:20"));
        lst_note.add(new Note("Name","Category","Priority","Done","2021-04-22 18:50:20","2021-04-22 18:50:20"));
        return  lst_note;
    }
}



