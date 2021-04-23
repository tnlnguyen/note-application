package com.uniapp.noteapplication.controller;

import android.os.AsyncTask;
import android.text.TextUtils;
import android.view.View;

import androidx.room.Room;

import com.uniapp.noteapplication.dao.StatusDao;
import com.uniapp.noteapplication.database.StatusDatabase;
import com.uniapp.noteapplication.model.Status;
import com.uniapp.noteapplication.view.IStatusView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class StatusController implements IStatusController {
    IStatusView statusView;
    View view;
    private StatusDatabase statusDatabase;
    String currentDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault()).format(new Date());


    public StatusController(IStatusView verificationView, View view) {
        this.statusView = verificationView;
        this.view = view;
        statusDatabase = Room.databaseBuilder(view.getContext(), StatusDatabase.class, StatusDatabase.DB_NAME).build();
    }

    @Override
    public void insertStatus(Map<String, Object> params) {
        try {
            StatusDao statusDao = statusDatabase.getStatusDao();
            Status status = new Status();
            String txtStatus = (String) params.get("status");
            status.setName(txtStatus);
            status.setDate(currentDate);

            if (!isEmpty(txtStatus)) {
                Executor myExecutor = Executors.newSingleThreadExecutor();
                myExecutor.execute(() -> {
                    statusDao.insertStatus(status);
                });
            } else {
                statusView.handleInsertEvent("Please fill all empty fields!",view);
            }
        } catch (Exception e) {
            statusView.handleInsertEvent(e.getMessage(),view);
        }
    }

    @Override
    public void editStatus(Map<String, Object> params) {
        try {
            StatusDao statusDao = statusDatabase.getStatusDao();
            Status status = (Status) params.get("status");

            if (!isEmpty(status.getName())) {
                Executor myExecutor = Executors.newSingleThreadExecutor();
                myExecutor.execute(() -> {
                    statusDao.updateStatus(status);
                });
            } else {
                statusView.handleInsertEvent("Please fill all empty fields!",view);
            }
        } catch (Exception e) {
            statusView.handleInsertEvent(e.getMessage(),view);
        }
    }

    @Override
    public void deleteStatus(Map<String, Object> params) {
        try {
            StatusDao statusDao = statusDatabase.getStatusDao();
            Status status = (Status) params.get("status");

            if (!isEmpty(status.getName())) {
                Executor myExecutor = Executors.newSingleThreadExecutor();
                myExecutor.execute(() -> {
                    statusDao.deleteStatus(status);

                });
            } else {
                statusView.handleInsertEvent("Please fill all empty fields!",view);
            }
        } catch (Exception e) {
            statusView.handleInsertEvent(e.getMessage(),view);
        }
    }

    @Override
    public void getListItem() {
        new getListItemTask().execute();
    }

    private class getListItemTask extends AsyncTask<Void, List<Status>, List<Status>> {

        @Override
        protected List<com.uniapp.noteapplication.model.Status> doInBackground(Void... voids) {
            StatusDao statusDao = statusDatabase.getStatusDao();
            List<com.uniapp.noteapplication.model.Status> statusList = statusDao.getAllStatus();
            return statusList;
        }

        @Override
        protected void onPostExecute(List<com.uniapp.noteapplication.model.Status> statuses) {
            super.onPostExecute(statuses);
            statusView.displayItem(view,statuses);
        }
    }

    @Override
    public boolean isEmpty(String textBox) {
        if(TextUtils.isEmpty(textBox))
            return true;
        else
            return false;
    }

}

