package com.example.note_managerment.ui.note;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
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
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.note_managerment.R;
import com.example.note_managerment.adapter.NoteAdapter;
import com.example.note_managerment.controller.CategoryController;
import com.example.note_managerment.controller.ICategoryController;
import com.example.note_managerment.controller.INoteController;
import com.example.note_managerment.controller.IPriorityController;
import com.example.note_managerment.controller.IStatusController;
import com.example.note_managerment.controller.NoteController;
import com.example.note_managerment.controller.PriorityController;
import com.example.note_managerment.controller.StatusController;
import com.example.note_managerment.database.CategoryDatabase;
import com.example.note_managerment.model.Category;
import com.example.note_managerment.model.Note;
import com.example.note_managerment.model.Priority;
import com.example.note_managerment.model.Status;
import com.example.note_managerment.view.ICategoryView;
import com.example.note_managerment.view.INoteView;
import com.example.note_managerment.view.IPriorityView;
import com.example.note_managerment.view.IStatusView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class NoteFragment extends Fragment implements INoteView, IStatusView, IPriorityView, ICategoryView {

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
    List<Category> categoryList;
    List<Status> statusList;
    List<Priority> priorityList;
    ArrayList<String> lst_category = new ArrayList<>();
    ArrayList<String> lst_priority = new ArrayList<>();
    ArrayList<String> lst_status = new ArrayList<>();

    INoteController noteController;
    IStatusController statusController;
    ICategoryController categoryController;
    IPriorityController priorityController;

    String currentDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault()).format(new Date());

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_note, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Inflate the layout for this fragment
        noteController  = new NoteController(this,view,getActivity());

        statusController = new StatusController(this,view,getActivity());

        categoryController = new CategoryController(this,view,getActivity());

        priorityController = new PriorityController(this,view,getActivity());

        //* Generate item on view *//*
        initVariable(view);
        categoryController.getListItem();

        statusController.getListItem();

        priorityController.getListItem();

        noteController.getListItem();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                insertNote(view);

            }
        }, 1000);

        //insertDialog.dismiss();

        //* Event initialization *//*

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

        for (Category category: categoryList
        ) {
            lst_category.add(category.getName());
        }

        for (Priority priority: priorityList
        ) {
            lst_priority.add(priority.getName());
        }

        for (Status status: statusList
        ) {
            lst_status.add(status.getName());
        }


        //load du lieu vao spinner
        addItemsToSpinner(lst_category,spinCategory);

        addItemsToSpinner(lst_priority,spinPriority);

        addItemsToSpinner(lst_status,spinStatus);

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
            note.setPriority(spinStatus.getSelectedItem().toString());
            note.setStatus(spinPriority.getSelectedItem().toString());

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
    public void insertCategory(View view) {

    }

    @Override
    public void insertPriority(View view) {

    }

    @Override
    public void insertStatus(View view) {

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
    public void displayItemStatus(View view, List<Status> status) {
        this.statusList = status;
    }

    @Override
    public void displayItemPriority(View view, List<Priority> priorityList) {
        this.priorityList = priorityList;
    }

    @Override
    public void displayItemCategory(View view, List<Category> category) {
        this.categoryList = category;
    }


    @Override
    public void displayItemNote(View view, List<Note> note) {
        adapter=new NoteAdapter(view,note,recyclerView,getActivity());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void handleInsertEvent(String message,View view) {
        Toast.makeText(view.getContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public FragmentActivity getFragmentActivity() {
        return null;
    }

    private void addItemsToSpinner(List<String> lst, Spinner spinner) {

        ArrayAdapter<String> adapter_category = new ArrayAdapter<String>(this.getContext(),
                android.R.layout.simple_spinner_item, lst
                );

        adapter_category.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter_category);

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
