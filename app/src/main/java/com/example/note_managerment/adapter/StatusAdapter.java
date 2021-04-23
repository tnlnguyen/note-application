package com.example.note_managerment.adapter;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.example.note_managerment.R;
import com.example.note_managerment.controller.IStatusController;
import com.example.note_managerment.controller.StatusController;
import com.example.note_managerment.database.StatusDatabase;
import com.example.note_managerment.model.Status;
import com.example.note_managerment.view.IStatusView;
import com.example.note_managerment.viewholder.StatusViewHolder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class StatusAdapter extends RecyclerView.Adapter<StatusViewHolder> implements IStatusAdapter,IStatusView {
    View view;
    List<Status> statusList;
    public Dialog editDialog;
    TextView tvTitle;
    EditText txtStatus;
    StatusDatabase userDatabase;
    Button closeDialog,editStatus;

    public StatusAdapter adapter;
    public RecyclerView recyclerView;

    IStatusController statusController;

    String currentDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault()).format(new Date());

    public StatusAdapter(View view, List<Status> statusList, RecyclerView recyclerView) {
        this.view = view;
        this.statusList = statusList;
        this.recyclerView = recyclerView;
    }

    @NonNull
    @Override
    public StatusViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemview= LayoutInflater.from(view.getContext())
                .inflate(R.layout.fragment_status_item, parent, false);

        return new StatusViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull StatusViewHolder holder, int position) {
        // Inflate the layout for this fragment
        statusController  = new StatusController(this,view);

        holder.name.setText(statusList.get(position).getName());
        holder.date.setText(statusList.get(position).getDate());



        holder.setItemClickListener((view, position1, isLongClick) -> {
            if(isLongClick)
            {
                //creating a popup menu

                editStatus(view,position1);

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
                                deleteStatus(view,position);
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
        return statusList.size();
    }

    @Override
    public void editStatus(View view,int position) {

        editDialog = new Dialog(view.getContext());
        editDialog.setContentView(R.layout.fragment_dialog);
        editDialog.setCancelable(false);
        txtStatus = editDialog.findViewById(R.id.txtName);
        closeDialog = editDialog.findViewById(R.id.close_catelgory);
        editStatus = editDialog.findViewById(R.id.add_category);
        tvTitle = editDialog.findViewById(R.id.txtTitle);

        tvTitle.setText("Status Form");
        txtStatus.setText(statusList.get(position).getName());

        editStatus.setText("Edit");

        editStatus.setOnClickListener(a -> {
            Map<String, Object> params = new HashMap<>();

            Status status = new Status();
            status.setId(statusList.get(position).getId());
            status.setName(txtStatus.getText().toString());
            status.setDate(currentDate);

            params.put("status", status);

            statusController.editStatus(params);
            statusController.getListItem();
            editDialog.dismiss();
        });

        closeDialog.setOnClickListener(c -> {
            editDialog.dismiss();
        });
    }

    @Override
    public void deleteStatus(View view, int position) {
        Map<String, Object> params = new HashMap<>();

        Status status = new Status();
        status.setId(statusList.get(position).getId());
        status.setName(statusList.get(position).getName());
        status.setDate(statusList.get(position).getDate());

        params.put("status", status);

        statusController.deleteStatus(params);
        statusController.getListItem();
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
    public void displayItem(View view, List<Status> status) {
        adapter=new StatusAdapter(view,status,recyclerView);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void handleInsertEvent(String message, View view) {
        Toast.makeText(view.getContext(), message, Toast.LENGTH_LONG).show();
    }

    public Status getItem(int position)
    {
        return statusList.get(position);
    }
}
