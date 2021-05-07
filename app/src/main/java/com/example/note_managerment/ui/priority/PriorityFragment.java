package com.example.note_managerment.ui.priority;

import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.note_managerment.R;
import com.example.note_managerment.adapter.PriorityAdapter;
import com.example.note_managerment.adapter.StatusAdapter;
import com.example.note_managerment.controller.IPriorityController;
import com.example.note_managerment.controller.IStatusController;
import com.example.note_managerment.controller.PriorityController;
import com.example.note_managerment.controller.StatusController;
import com.example.note_managerment.database.CategoryDatabase;
import com.example.note_managerment.model.Priority;
import com.example.note_managerment.model.Status;
import com.example.note_managerment.view.IPriorityView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PriorityFragment extends Fragment implements IPriorityView {

    public PriorityAdapter adapter;
    public RecyclerView recyclerView;
    LinearLayoutManager layoutManager;

    //* Application variables *//*
    FloatingActionButton categoryPlus;
    TextView tvTitle;
    EditText txtPriority;
    CategoryDatabase userDatabase;
    Button closeDialog,addPriority;
    public Dialog insertDialog;

    IPriorityController priorityController;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_priority, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Inflate the layout for this fragment
        priorityController  = new PriorityController(this,view);
        //* Generate item on view *//*
        initVariable(view);
        priorityController.getListItem();


        //* Event initialization *//*
        insertPriority(view);
    }

    @Override
    public void insertPriority(View view) {
        insertDialog = new Dialog(view.getContext());
        insertDialog.setContentView(R.layout.fragment_dialog);
        insertDialog.setCancelable(false);
        txtPriority = insertDialog.findViewById(R.id.txtName);
        closeDialog = insertDialog.findViewById(R.id.close_catelgory);
        addPriority = insertDialog.findViewById(R.id.add_category);
        tvTitle = insertDialog.findViewById(R.id.txtTitle);

        tvTitle.setText("Priority Form");

        categoryPlus.setOnClickListener(v -> {
            insertDialog.show();
        });

        addPriority.setOnClickListener(a -> {
            Map<String, Object> params = new HashMap<>();
            params.put("status", txtPriority.getText().toString());

            priorityController.insertPriority(params);
            priorityController.getListItem();

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
    public void displayItem(View view, List<Priority> priorityList) {
        adapter=new PriorityAdapter(view,priorityList,recyclerView);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void handleInsertEvent(String message,View view) {
        Toast.makeText(view.getContext(), message, Toast.LENGTH_LONG).show();
    }
}