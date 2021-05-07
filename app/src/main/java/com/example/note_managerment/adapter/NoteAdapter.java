package com.example.note_managerment.adapter;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Gravity;
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
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.note_managerment.R;
import com.example.note_managerment.controller.CategoryController;
import com.example.note_managerment.controller.ICategoryController;
import com.example.note_managerment.controller.INoteController;
import com.example.note_managerment.controller.IPriorityController;
import com.example.note_managerment.controller.IStatusController;
import com.example.note_managerment.controller.NoteController;
import com.example.note_managerment.controller.PriorityController;
import com.example.note_managerment.controller.StatusController;
import com.example.note_managerment.model.Category;
import com.example.note_managerment.model.Note;
import com.example.note_managerment.model.Priority;
import com.example.note_managerment.model.Status;
import com.example.note_managerment.view.ICategoryView;
import com.example.note_managerment.view.INoteView;
import com.example.note_managerment.view.IPriorityView;
import com.example.note_managerment.view.IStatusView;
import com.example.note_managerment.viewholder.NoteViewHolder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class NoteAdapter extends RecyclerView.Adapter<NoteViewHolder> implements INoteAdapter, INoteView , IStatusView, IPriorityView, ICategoryView {
    View view;
    List<Note> noteList;
    public Dialog editDialog;
    EditText edtNote;
    TextView tvPlanDate;
    Button btnAddEdit,btnClose,btChooserDate;
    Spinner spinCategory, spinPriority, spinStatus;
    private int lastSelectedYear = -1,lastSelectedMonth =-1 ,lastSelectedDayOfMonth =-1;
    FragmentActivity fragmentActivity;
    public NoteAdapter adapter;
    public RecyclerView recyclerView;

    INoteController noteController;
    IStatusController statusController;
    ICategoryController categoryController;
    IPriorityController priorityController;

    List<Category> categoryList;
    List<Status> statusList;
    List<Priority> priorityList;
    ArrayList<String> lst_category = new ArrayList<>();
    ArrayList<String> lst_priority = new ArrayList<>();
    ArrayList<String> lst_status = new ArrayList<>();

    String currentDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault()).format(new Date());

    public NoteAdapter(View view, List<Note> noteList, RecyclerView recyclerView, FragmentActivity fragmentActivity) {
        this.view = view;
        this.noteList = noteList;
        this.recyclerView = recyclerView;
        this.fragmentActivity = fragmentActivity;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemview= LayoutInflater.from(view.getContext())
                .inflate(R.layout.fragment_note_item, parent, false);

        return new NoteViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        // Inflate the layout for this fragment
        noteController  = new NoteController(this,view);

        statusController = new StatusController(this,view,fragmentActivity);

        categoryController = new CategoryController(this,view,fragmentActivity);

        priorityController = new PriorityController(this,view,fragmentActivity);

        holder.txtName.setText(noteList.get(position).getName());
        holder.txtCategory.setText(noteList.get(position).getCategory());
        holder.txtPriority.setText(noteList.get(position).getPriority());
        holder.txtStatus.setText(noteList.get(position).getStatus());
        holder.txtPlanDate.setText(noteList.get(position).getPlanDate());
        holder.txtCreatedDate.setText(noteList.get(position).getCreatedDate());


        holder.setItemClickListener((view, position1, isLongClick) -> {
            if(isLongClick)
            {
                //creating a popup menu
                categoryController.getListItem();

                statusController.getListItem();

                priorityController.getListItem();

                noteController.getListItem();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        editNote(view,position1);

                    }
                }, 1000);

                PopupMenu popup = new PopupMenu(view.getContext(), holder.itemView);
                //inflating menu from xml resource
                popup.inflate(R.menu.option_menu);
                popup.setGravity(Gravity.RIGHT);
                //adding click listener
                popup.setOnMenuItemClickListener(item -> {
                    switch (item.getItemId()) {
                        case R.id.edit:
                            editDialog.show();
                            break;
                        case R.id.delete:
                            deleteNote(view,position);
                            break;
                    }
                    return false;
                });
                //displaying the popup
                popup.show();
            }
            else
            {
                //show dialog
                //showDialog("Status Form",null);
            }
        });
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

    @Override
    public void editNote(View view,int position) {

        editDialog = new Dialog(view.getContext());
        editDialog.setContentView(R.layout.fragment_dialog_note);
        editDialog.setCancelable(false);
        btnAddEdit = (Button) editDialog.findViewById(R.id.btAdd);
        btnClose = (Button) editDialog.findViewById(R.id.btClose);
        btChooserDate = (Button) editDialog.findViewById(R.id.btChooseDate);
        tvPlanDate = (TextView) editDialog.findViewById(R.id.tvPlanDate);
        spinCategory = (Spinner) editDialog.findViewById(R.id.spinCategory);
        spinPriority = (Spinner) editDialog.findViewById(R.id.spinPriority);
        spinStatus = (Spinner) editDialog.findViewById(R.id.spinStatus);
        edtNote = (EditText) editDialog.findViewById(R.id.edtNote);

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
        addItemsToSpinner(lst_category,spinCategory,view,noteList.get(position).getCategory());

        addItemsToSpinner(lst_priority,spinPriority,view,noteList.get(position).getPriority());

        addItemsToSpinner(lst_status,spinStatus,view,noteList.get(position).getStatus());

        edtNote.setText(noteList.get(position).getName());
        tvPlanDate.setText(noteList.get(position).getPlanDate());

        btnAddEdit.setText("Edit");

        btnAddEdit.setOnClickListener(a -> {
            Map<String, Object> params = new HashMap<>();

            Note note = new Note();
            note.setId(noteList.get(position).getId());
            note.setName(edtNote.getText().toString().trim());
            note.setCategory(spinCategory.getSelectedItem().toString());
            note.setPriority(spinPriority.getSelectedItem().toString());
            note.setStatus(spinStatus.getSelectedItem().toString());
            note.setPlanDate(tvPlanDate.getText().toString());
            note.setCreatedDate(currentDate);

            params.put("note", note);

            noteController.editNote(params);
            noteController.getListItem();
            notifyDataSetChanged();
            editDialog.dismiss();
        });

        btnClose.setOnClickListener(c -> {
            editDialog.dismiss();
        });

        btChooserDate.setOnClickListener(v -> {
            buttonSelectDate(v,tvPlanDate);
        });

    }

    @Override
    public void deleteNote(View view, int position) {
        Map<String, Object> params = new HashMap<>();

        Note note = new Note();
        note.setId(noteList.get(position).getId());
        note.setName(edtNote.getText().toString().trim());
/*        note.setCategory(spinCategory.getSelectedItem().toString());
        note.setPriority(spinPriority.getSelectedItem().toString());
        note.setStatus(spinStatus.getSelectedItem().toString());*/
        note.setPlanDate(tvPlanDate.getText().toString());
        note.setCreatedDate(currentDate);

        params.put("note", note);

        noteController.deleteNote(params);
        noteController.getListItem();
        notifyDataSetChanged();
    }

    @Override
    public void insertNote(View view) {

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
    public void displayItem(View view, List<Note> status) {

    }

    @Override
    public void displayItemNote(View view, List<Note> note) {
        adapter=new NoteAdapter(view,note,recyclerView,fragmentActivity);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void handleInsertEvent(String message, View view) {
        Toast.makeText(view.getContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public FragmentActivity getFragmentActivity() {
        return null;
    }

    public Note getItem(int position)
    {
        return noteList.get(position);
    }

    private void addItemsToSpinner(List<String> lst, Spinner spinner, View view, String str_set) {

        ArrayAdapter<String> adapter_category = new ArrayAdapter<String>(view.getContext(),
                android.R.layout.simple_spinner_item, lst
        );

        adapter_category.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter_category);

        spinner.setSelection(adapter_category.getPosition(str_set));

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
        datePickerDialog_end = new DatePickerDialog(view.getContext(),
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
