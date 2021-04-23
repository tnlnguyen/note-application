package com.uniapp.noteapplication.adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.uniapp.noteapplication.R;
import com.uniapp.noteapplication.database.StatusDatabase;
import com.uniapp.noteapplication.model.INoteView;
import com.uniapp.noteapplication.model.Note;
import com.uniapp.noteapplication.viewholder.NoteViewHolder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;

public class NoteAdapter extends RecyclerView.Adapter<NoteViewHolder>  implements INoteView {
    Context context;
    List<Note> lst_note_view;
    Dialog dialog;

    EditText edtNote;
    TextView tvPlanDate;
    Button btnAddEdit,btnClose,btChooserDate;
    Spinner spinCategory, spinPriority, spinStatus;

    StatusDatabase userDatabase;

    String currentDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault()).format(new Date());

    public NoteAdapter(List<Note> lst_item, Context context)
    {
        this.lst_note_view = lst_item;
        this.context = context;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.note_item,parent,false);
        return new NoteViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        holder.txtName.setText("Name: " +lst_note_view.get(position).getName());
        holder.txtCategory.setText("Category: " +lst_note_view.get(position).getCategory());
        holder.txtPriority.setText("Priority: " +lst_note_view.get(position).getPriority());
        holder.txtStatus.setText("Status: " +lst_note_view.get(position).getStatus());
        holder.txtPlanDate.setText("Plan date: " +lst_note_view.get(position).getPlanDate());
        holder.txtCreatedDate.setText("Created date: " +lst_note_view.get(position).getCreatedDate());

        holder.setItemClickListener((view, position1, isLongClick) -> {
            if(isLongClick)
            {
                //creating a popup menu
                PopupMenu popup = new PopupMenu(context, holder.itemView);
                //inflating menu from xml resource
                popup.inflate(R.menu.option_menu);
                popup.setGravity(Gravity.RIGHT);
                //adding click listener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId()) {
                            case R.id.edit:
                                //handle menu1 click
                                openDialog(Gravity.CENTER,view,R.layout.dialog_note,"Status Form",false);
                                break;
                            case R.id.delete:
                                //handle menu2 click

                                break;
                        }
                        return false;
                    }
                });
                //displaying the popup
                popup.show();
            }
            else
            {
                //show dialog
                //showDialog("Status Form",null);
            }
            notifyItemChanged(position1);

        });
    }

    @Override
    public int getItemCount() {
        return lst_note_view.size();
    }

    @Override
    public void openDialog(int gravity, View view, @LayoutRes int layoutResID, String title, boolean isAdd) {
        dialog = new Dialog(view.getContext());
        dialog.setContentView(layoutResID);
        dialog.setCancelable(false);
        //dialogOTPLoading alignment

        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity= Gravity.CENTER;
        window.setAttributes(windowAttributes);

        btnAddEdit = (Button) dialog.findViewById(R.id.add_category);

        btnClose = (Button) dialog.findViewById(R.id.close_catelgory);

        btChooserDate = (Button) dialog.findViewById(R.id.btChooseDate);

        tvPlanDate = (TextView) dialog.findViewById(R.id.tvPlanDate);

        spinCategory = (Spinner) dialog.findViewById(R.id.spinCategory);

        spinPriority = (Spinner) dialog.findViewById(R.id.spinPriority);

        spinStatus = (Spinner) dialog.findViewById(R.id.spinStatus);

        edtNote = (EditText) dialog.findViewById(R.id.edtNote);

        if(isAdd)
        {
            btnAddEdit.setText("Add");
            btnAddEdit.setOnClickListener(v ->{

            });
        }
        else
        {
            btnAddEdit.setText("Edit");
            //load du lieu do vao edit text
            btnAddEdit.setOnClickListener(v ->{

            });
        }

        btnClose.setOnClickListener(v -> {
            //close dialog
            dialog.dismiss();
        });

        dialog.show();
    }

    @Override
    public void initView(View view) {

    }

    @Override
    public boolean isEmpty() {
        if(TextUtils.isEmpty(edtNote.getText().toString()))
            return true;
        else
            return false;
    }
}
