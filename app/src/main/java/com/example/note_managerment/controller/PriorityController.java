package com.example.note_managerment.controller;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.view.View;

import androidx.fragment.app.FragmentActivity;
import androidx.room.Room;

import com.example.note_managerment.dao.PriorityDao;
import com.example.note_managerment.database.CategoryDatabase;
import com.example.note_managerment.model.Priority;
import com.example.note_managerment.view.IPriorityView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class PriorityController implements IPriorityController {
    IPriorityView priorityView;
    View view;
    private CategoryDatabase statusDatabase;
    FragmentActivity fragmentActivity;
    String currentDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault()).format(new Date());


    public PriorityController(IPriorityView verificationView, View view, FragmentActivity fragmentActivity) {
        this.priorityView = verificationView;
        this.view = view;
        this.fragmentActivity = fragmentActivity;
        /*statusDatabase = Room.databaseBuilder((Context) verificationView, StatusDatabase.class, StatusDatabase.DB_NAME).build();*/
        statusDatabase = Room.databaseBuilder(view.getContext(), CategoryDatabase.class, CategoryDatabase.DB_NAME).build();
    }

    public PriorityController() {

    }

    @Override
    public void insertPriority(Map<String, Object> params) {
        try {
            PriorityDao priorityDao = statusDatabase.getPriorityDao();
            Priority priority = new Priority();
            String txtPriority = (String) params.get("priority");
            priority.setName(txtPriority);
            priority.setDate(currentDate);

            if (!priorityView.isEmpty(txtPriority)) {
                Executor myExecutor = Executors.newSingleThreadExecutor();
                myExecutor.execute(() -> {
                    priorityDao.insertPriority(priority);

                });
            } else {
                priorityView.handleInsertEvent("Please fill all empty fields!",view);
            }
        } catch (Exception e) {
            priorityView.handleInsertEvent(e.getMessage(),view);
        }
    }

    @Override
    public void editPriority(Map<String, Object> params) {
        try {
            PriorityDao priorityDao = statusDatabase.getPriorityDao();
            Priority priority = (Priority) params.get("priority");

            if (!priorityView.isEmpty(priority.getName())) {
                Executor myExecutor = Executors.newSingleThreadExecutor();
                myExecutor.execute(() -> {
                    priorityDao.updatePriority(priority);

                });
            } else {
                priorityView.handleInsertEvent("Please fill all empty fields!",view);
            }
        } catch (Exception e) {
            priorityView.handleInsertEvent(e.getMessage(),view);
        }
    }

    @Override
    public void deletePriority(Map<String, Object> params) {
        try {
            PriorityDao priorityDao = statusDatabase.getPriorityDao();
            Priority priority = (Priority) params.get("priority");

            if (!priorityView.isEmpty(priority.getName())) {
                Executor myExecutor = Executors.newSingleThreadExecutor();
                myExecutor.execute(() -> {
                    priorityDao.deletePriority(priority);

                });
            } else {
                priorityView.handleInsertEvent("Please fill all empty fields!",view);
            }
        } catch (Exception e) {
            priorityView.handleInsertEvent(e.getMessage(),view);
        }
    }

    @Override
    public void getListItem() {
        new getListItemTask(fragmentActivity).execute();
    }

    private class getListItemTask extends AsyncTask<Void, List<Priority>, List<Priority>> {
        private ProgressDialog dialog;

        public getListItemTask(FragmentActivity fragmentActivity) {
            dialog = new ProgressDialog(fragmentActivity);
        }

        @Override
        protected void onPreExecute() {
            dialog.setMessage("Doing something, please wait.");
            dialog.show();
        }
        @Override
        public List<com.example.note_managerment.model.Priority> doInBackground(Void... maps) {
            PriorityDao priorityDao = statusDatabase.getPriorityDao();
            List<com.example.note_managerment.model.Priority> priorityList = priorityDao.getAllPriority();
            return priorityList;
        }

        @Override
        protected void onPostExecute(List<com.example.note_managerment.model.Priority> priorityList) {
            super.onPostExecute(priorityList);
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            priorityView.displayItemPriority(view,priorityList);
        }
    }

}
