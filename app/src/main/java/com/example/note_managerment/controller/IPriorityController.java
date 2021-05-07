package com.example.note_managerment.controller;

import java.util.Map;

public interface IPriorityController {
    void insertPriority(Map<String, Object> params);
    void editPriority(Map<String, Object> params);
    void deletePriority(Map<String, Object> params);
    void getListItem();
}
