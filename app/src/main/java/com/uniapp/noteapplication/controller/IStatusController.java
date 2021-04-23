package com.uniapp.noteapplication.controller;

import java.util.Map;

public interface IStatusController {
    void insertStatus(Map<String, Object> params);

    void editStatus(Map<String, Object> params);

    void deleteStatus(Map<String, Object> params);

    void getListItem();

    boolean isEmpty(String textBox);

}
