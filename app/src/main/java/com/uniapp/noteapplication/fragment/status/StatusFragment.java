package com.uniapp.noteapplication.fragment.status;

import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.uniapp.noteapplication.R;
import com.uniapp.noteapplication.adapter.StatusAdapter;
import com.uniapp.noteapplication.controller.IStatusController;
import com.uniapp.noteapplication.controller.StatusController;
import com.uniapp.noteapplication.model.Status;
import com.uniapp.noteapplication.view.IStatusView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class StatusFragment extends Fragment implements IStatusView {

    public StatusAdapter adapter;
    public RecyclerView recyclerView;
    LinearLayoutManager layoutManager;

    //* Application variables *//*
    FloatingActionButton categoryPlus;
    List<Status> statusList;
    TextView tvTitle;
    EditText txtStatus;
    Button closeDialog,addStatus, editStatus;
    public Dialog insertDialog;
    public Dialog editDialog;
    String currentDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault()).format(new Date());

    IStatusController statusController;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_status, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Inflate the layout for this fragment
        statusController  = new StatusController(this,view);
        /* Generate item on view */
        initVariable(view);
        statusController.getListItem();


        /* Event initialization */
        insertStatus(view);
    }

    @Override
    public void insertStatus(View view) {
        insertDialog = new Dialog(view.getContext());
        insertDialog.setContentView(R.layout.fragment_dialog);
        insertDialog.setCancelable(false);
        txtStatus = insertDialog.findViewById(R.id.txtName);
        closeDialog = insertDialog.findViewById(R.id.close_catelgory);
        addStatus = insertDialog.findViewById(R.id.add_category);
        tvTitle = insertDialog.findViewById(R.id.txtTitle);

        tvTitle.setText("Status Form");

        categoryPlus.setOnClickListener(v -> {
            insertDialog.show();
        });

        addStatus.setOnClickListener(a -> {
            Map<String, Object> params = new HashMap<>();
            params.put("status", txtStatus.getText().toString());

            statusController.insertStatus(params);
            statusController.getListItem();
            insertDialog.dismiss();
        });

        closeDialog.setOnClickListener(c -> {
            insertDialog.dismiss();
        });
    }

    @Override
    public void initVariable(View view) {
        categoryPlus= (FloatingActionButton) view.findViewById(R.id.btnPlus);
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
    public void displayItem(View view,List<Status> status) {
        adapter=new StatusAdapter(view, status, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void handleInsertEvent(String message,View view) {
        Toast.makeText(view.getContext(), message, Toast.LENGTH_LONG).show();
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

}
