package com.example.note_managerment.ui.note;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.note_managerment.R;
import com.example.note_managerment.adapter.NoteAdapter;
import com.example.note_managerment.adapter.StatusAdapter;
import com.example.note_managerment.controller.INoteController;
import com.example.note_managerment.controller.IStatusController;
import com.example.note_managerment.controller.NoteController;
import com.example.note_managerment.controller.StatusController;
import com.example.note_managerment.database.CategoryDatabase;
import com.example.note_managerment.model.Note;
import com.example.note_managerment.model.Status;
import com.example.note_managerment.view.INoteView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class NoteFragment extends Fragment implements INoteView {

    public NoteAdapter adapter;
    public RecyclerView recyclerView;
    LinearLayoutManager layoutManager;

    //* Application variables *//*
    FloatingActionButton notePlus;
    CategoryDatabase userDatabase;
    EditText edtNote;
    TextView tvPlanDate;
    Button btnAddEdit,btnClose,btChooserDate;
    Spinner spinCategory, spinPriority, spinStatus;
    private int lastSelectedYear = -1,lastSelectedMonth =-1 ,lastSelectedDayOfMonth =-1;
    DatePickerDialog datePickerDialog_end;
    public Dialog insertDialog;

    INoteController noteController;

    String currentDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault()).format(new Date());

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_note, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Inflate the layout for this fragment
        noteController  = new NoteController(this,view);
        //* Generate item on view *//*
        initVariable(view);
        noteController.getListItem();


        //* Event initialization *//*
        insertNote(view);
    }

    @Override
    public void insertNote(View view) {
        insertDialog = new Dialog(view.getContext());
        insertDialog.setContentView(R.layout.fragment_dialog_note);
        insertDialog.setCancelable(false);
        btnAddEdit = (Button) insertDialog.findViewById(R.id.btAdd);
        btnClose = (Button) insertDialog.findViewById(R.id.btClose);
        btChooserDate = (Button) insertDialog.findViewById(R.id.btChooseDate);
        tvPlanDate = (TextView) insertDialog.findViewById(R.id.tvPlanDate);
        spinCategory = (Spinner) insertDialog.findViewById(R.id.spinCategory);
        spinPriority = (Spinner) insertDialog.findViewById(R.id.spinPriority);
        spinStatus = (Spinner) insertDialog.findViewById(R.id.spinStatus);
        edtNote = (EditText) insertDialog.findViewById(R.id.edtNote);

        btnAddEdit.setText("Add");

        //load du lieu vao spinner


        notePlus.setOnClickListener(v -> {
            insertDialog.show();
        });

        btnAddEdit.setOnClickListener(a -> {
            Map<String, Object> params = new HashMap<>();
            Note note = new Note();
            note.setName(edtNote.getText().toString());
            note.setPlanDate(tvPlanDate.getText().toString());
            note.setCreatedDate(currentDate);
            note.setCategory(spinCategory.getSelectedItem().toString());
            note.setPriority(spinPriority.getSelectedItem().toString());
            note.setStatus(spinStatus.getSelectedItem().toString());

            params.put("note", note);

            noteController.insertNote(params);
            noteController.getListItem();

            insertDialog.dismiss();
        });

        btChooserDate.setOnClickListener(v -> {
            buttonSelectDate(v,tvPlanDate);
        });

        btnClose.setOnClickListener(c -> {
            insertDialog.dismiss();
        });
    }

    @Override
    public void initVariable(View view) {
        notePlus= (FloatingActionButton) view.findViewById(R.id.btnPlus);
        recyclerView= (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);
        registerForContextMenu(recyclerView);
    }

    @Override
    public boolean isEmpty(String textBox) {
        if(TextUtils.isEmpty(textBox))
            return true;
        else
            return false;

    }

    @Override
    public void displayItem(View view, List<Note> note) {
        adapter=new NoteAdapter(view,note,recyclerView);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void handleInsertEvent(String message,View view) {
        Toast.makeText(view.getContext(), message, Toast.LENGTH_LONG).show();
    }

    private void addItemsToSpinner(String[] listInfor,Spinner spin) {

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getContext(),
                android.R.layout.simple_spinner_item,
                listInfor);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spin.setAdapter(adapter);
    }

    private void buttonSelectDate(View v, TextView datetext) {

        DatePickerDialog.OnDateSetListener dateSetListener_end = (view, year, monthOfYear, dayOfMonth) -> {
            // Display the formatted date
            datetext.setText(changeFormat(year,monthOfYear,dayOfMonth) );
            datetext.setTextColor(Color.BLACK);

            lastSelectedYear = year;
            lastSelectedMonth = monthOfYear;
            lastSelectedDayOfMonth = dayOfMonth;
        };
        DatePickerDialog datePickerDialog_end = null;
        datePickerDialog_end = new DatePickerDialog(this.getContext(),
                dateSetListener_end, lastSelectedYear, lastSelectedMonth, lastSelectedDayOfMonth);
        // Show
        datePickerDialog_end.show();

    }

    private String changeFormat(int year,int monthOfYear, int dayOfMonth) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, monthOfYear, dayOfMonth, 0, 0, 0);
        Date chosenDate = cal.getTime();

        String df_full_str = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault()).format(chosenDate);
        return  df_full_str;
    }
}
