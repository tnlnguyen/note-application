package com.uniapp.noteapplication.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "status")
public class Status {

    @PrimaryKey(autoGenerate = true)
    Integer id;

    @ColumnInfo(name = "name")
    String name;

    @ColumnInfo(name = "date")
    String date;

    public Status() {

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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public Status(String name, String date) {
        this.name = name;
        this.date = date;
    }

}
