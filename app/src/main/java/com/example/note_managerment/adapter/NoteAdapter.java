package com.example.note_managerment.adapter;

import android.app.Dialog;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.example.note_managerment.R;
import com.example.note_managerment.controller.INoteController;
import com.example.note_managerment.controller.NoteController;
import com.example.note_managerment.model.Note;
import com.example.note_managerment.view.INoteView;
import com.example.note_managerment.viewholder.NoteViewHolder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class NoteAdapter extends RecyclerView.Adapter<NoteViewHolder> implements INoteAdapter, INoteView {
    View view;
    List<Note> noteList;
    public Dialog editDialog;
    EditText edtNote;
    TextView tvPlanDate;
    Button btnAddEdit,btnClose,btChooserDate;
    Spinner spinCategory, spinPriority, spinStatus;

    public NoteAdapter adapter;
    public RecyclerView recyclerView;

    INoteController noteController;

    String currentDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault()).format(new Date());

    public NoteAdapter(View view, List<Note> noteList, RecyclerView recyclerView) {
        this.view = view;
        this.noteList = noteList;
        this.recyclerView = recyclerView;
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

                editNote(view,position1);

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
        editDialog.setContentView(R.layout.fragment_dialog);
        editDialog.setCancelable(false);
        btnAddEdit = (Button) editDialog.findViewById(R.id.add_category);
        btnClose = (Button) editDialog.findViewById(R.id.close_catelgory);
        btChooserDate = (Button) editDialog.findViewById(R.id.btChooseDate);
        tvPlanDate = (TextView) editDialog.findViewById(R.id.tvPlanDate);
        spinCategory = (Spinner) editDialog.findViewById(R.id.spinCategory);
        spinPriority = (Spinner) editDialog.findViewById(R.id.spinPriority);
        spinStatus = (Spinner) editDialog.findViewById(R.id.spinStatus);
        edtNote = (EditText) editDialog.findViewById(R.id.edtNote);

        edtNote.setText(noteList.get(position).getName());
        tvPlanDate.setText(noteList.get(position).getPlanDate());
        //thieu 3 cai spinner

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
    }

    @Override
    public void deleteNote(View view, int position) {
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

        noteController.deleteNote(params);
        noteController.getListItem();
        notifyDataSetChanged();
    }

    @Override
    public void insertNote(View view) {

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
    public void displayItem(View view, List<Note> note) {
        adapter=new NoteAdapter(view,note,recyclerView);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void handleInsertEvent(String message, View view) {
        Toast.makeText(view.getContext(), message, Toast.LENGTH_LONG).show();
    }

    public Note getItem(int position)
    {
        return noteList.get(position);
    }
}
