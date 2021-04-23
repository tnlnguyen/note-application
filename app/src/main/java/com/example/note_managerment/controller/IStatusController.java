package com.example.note_managerment.controller;

import java.util.Map;

public interface IStatusController {
    void insertStatus(Map<String, Object> params);
    void editStatus(Map<String, Object> params);
    void deleteStatus(Map<String, Object> params);
    void getListItem();
}
