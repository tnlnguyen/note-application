package com.example.note_managerment.controller;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;

import androidx.room.Room;

import com.example.note_managerment.dao.StatusDao;
import com.example.note_managerment.database.StatusDatabase;
import com.example.note_managerment.model.Status;
import com.example.note_managerment.view.IStatusView;

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
        /*statusDatabase = Room.databaseBuilder((Context) verificationView, StatusDatabase.class, StatusDatabase.DB_NAME).build();*/
        statusDatabase = Room.databaseBuilder(view.getContext(), StatusDatabase.class, StatusDatabase.DB_NAME).build();
    }

    public StatusController() {

    }

    @Override
    public void insertStatus(Map<String, Object> params) {
        try {
            StatusDao statusDao = statusDatabase.getStatusDao();
            Status status = new Status();
            String txtStatus = (String) params.get("status");
            status.setName(txtStatus);
            status.setDate(currentDate);

            if (!statusView.isEmpty(txtStatus)) {
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
    public void getListItem() {
        new getListItemTask().execute();
    }

    private class getListItemTask extends AsyncTask<Void, List<Status>, List<Status>> {
        @Override
        public List<com.example.note_managerment.model.Status> doInBackground(Void... maps) {
            StatusDao statusDao = statusDatabase.getStatusDao();
            List<com.example.note_managerment.model.Status> statusList = statusDao.getAllStatus();
            return statusList;
        }

        @Override
        protected void onPostExecute(List<com.example.note_managerment.model.Status> statusList) {
            super.onPostExecute(statusList);
            statusView.displayItem(view,statusList);
        }
    }

}
