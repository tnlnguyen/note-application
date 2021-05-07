package com.example.note_managerment.controller;

import java.util.Map;

public interface INoteController {
    void insertNote(Map<String, Object> params);
    void editNote(Map<String, Object> params);
    void deleteNote(Map<String, Object> params);
    void getListItem();

}
