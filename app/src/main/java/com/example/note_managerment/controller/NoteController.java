package com.example.note_managerment.controller;

import android.os.AsyncTask;
import android.view.View;

import androidx.room.Room;

import com.example.note_managerment.dao.NoteDao;
import com.example.note_managerment.dao.StatusDao;
import com.example.note_managerment.database.CategoryDatabase;
import com.example.note_managerment.model.Note;
import com.example.note_managerment.model.Status;
import com.example.note_managerment.view.INoteView;
import com.example.note_managerment.view.IStatusView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class NoteController implements INoteController {
    INoteView noteView;
    View view;
    private CategoryDatabase statusDatabase;
    String currentDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault()).format(new Date());


    public NoteController(INoteView verificationView, View view) {
        this.noteView = verificationView;
        this.view = view;
        /*statusDatabase = Room.databaseBuilder((Context) verificationView, StatusDatabase.class, StatusDatabase.DB_NAME).build();*/
        statusDatabase = Room.databaseBuilder(view.getContext(), CategoryDatabase.class, CategoryDatabase.DB_NAME).build();
    }

    public NoteController() {

    }

    @Override
    public void insertNote(Map<String, Object> params) {
        try {
            NoteDao noteDao = statusDatabase.getNoteDao();
            Note note = (Note) params.get("note");

            if (!noteView.isEmpty(note.getName())) {
                Executor myExecutor = Executors.newSingleThreadExecutor();
                myExecutor.execute(() -> {
                    noteDao.insertNote(note);

                });
            } else {
                noteView.handleInsertEvent("Please fill all empty fields!",view);
            }
        } catch (Exception e) {
            noteView.handleInsertEvent(e.getMessage(),view);
        }
    }

    @Override
    public void editNote(Map<String, Object> params) {
        try {
            NoteDao noteDao = statusDatabase.getNoteDao();
            Note note = (Note) params.get("note");

            if (!noteView.isEmpty(note.getName())) {
                Executor myExecutor = Executors.newSingleThreadExecutor();
                myExecutor.execute(() -> {
                    noteDao.updateNote(note);

                });
            } else {
                noteView.handleInsertEvent("Please fill all empty fields!",view);
            }
        } catch (Exception e) {
            noteView.handleInsertEvent(e.getMessage(),view);
        }
    }

    @Override
    public void deleteNote(Map<String, Object> params) {
        try {
            NoteDao noteDao = statusDatabase.getNoteDao();
            Note note = (Note) params.get("note");

            if (!noteView.isEmpty(note.getName())) {
                Executor myExecutor = Executors.newSingleThreadExecutor();
                myExecutor.execute(() -> {
                    noteDao.deleteNote(note);

                });
            } else {
                noteView.handleInsertEvent("Please fill all empty fields!",view);
            }
        } catch (Exception e) {
            noteView.handleInsertEvent(e.getMessage(),view);
        }
    }

    @Override
    public void getListItem() {
        new getListItemTask().execute();
    }

    private class getListItemTask extends AsyncTask<Void, List<Note>, List<Note>> {
        @Override
        public List<com.example.note_managerment.model.Note> doInBackground(Void... maps) {
            NoteDao noteDao = statusDatabase.getNoteDao();
            List<com.example.note_managerment.model.Note> notelist = noteDao.getAllNote();
            return notelist;
        }

        @Override
        protected void onPostExecute(List<com.example.note_managerment.model.Note> notelist) {
            super.onPostExecute(notelist);
            noteView.displayItem(view,notelist);
        }
    }
}
