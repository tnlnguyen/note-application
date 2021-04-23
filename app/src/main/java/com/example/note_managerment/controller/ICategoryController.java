package com.example.note_managerment.controller;

import java.util.Map;

public interface ICategoryController {
    void insertCategory(Map<String, Object> params);
    void editCategory(Map<String, Object> params);
    void deleteCategory(Map<String, Object> params);
    void getListItem();
}
