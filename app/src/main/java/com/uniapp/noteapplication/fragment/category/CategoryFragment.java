package com.uniapp.noteapplication.fragment.category;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.uniapp.noteapplication.R;
import com.uniapp.noteapplication.adapter.CategoryAdapter;
import com.uniapp.noteapplication.controller.CategoryController;
import com.uniapp.noteapplication.controller.ICategoryController;
import com.uniapp.noteapplication.model.Category;
import com.uniapp.noteapplication.view.ICategoryView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategoryFragment extends Fragment implements ICategoryView {

    /* Recycle view variables */
    public CategoryAdapter adapter;
    public RecyclerView recyclerView;
    LinearLayoutManager layoutManager;

    /* Application variables */
    FloatingActionButton categoryPlus;
    EditText txtCategory;
    Button closeDialog;
    Button addCategory;
    ProgressDialog progressDialog;
    public Dialog insertDialog;

    ICategoryController categoryController;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_category, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        categoryController  = new CategoryController(this, view);

        /* Generate item on view */
        initVariable(view);
        categoryController.getListItem();

        /* Event initialization */
        insertCategory(view);
    }

    @Override
    public void insertCategory(View view) {
        insertDialog = new Dialog(view.getContext());
        insertDialog.setContentView(R.layout.fragment_category_dialog);
        insertDialog.setCancelable(false);

        txtCategory = insertDialog.findViewById(R.id.txt_category);
        closeDialog = insertDialog.findViewById(R.id.close_catelgory);
        addCategory = insertDialog.findViewById(R.id.add_category);

        categoryPlus.setOnClickListener(v -> {
            insertDialog.show();
        });

        addCategory.setOnClickListener(a -> {
            Map<String, Object> params = new HashMap<>();
            params.put("category", txtCategory.getText().toString());

            insertDialog.dismiss();
            processDialogEnable(view);
            categoryController.insertCategory(params);
            categoryController.getListItem();
        });

        closeDialog.setOnClickListener(c -> {
            insertDialog.dismiss();
        });
    }

    /* Initialization functions */
    @Override
    public void initVariable(View view) {
        categoryPlus= (FloatingActionButton) view.findViewById(R.id.categoryPlus);
        recyclerView= (RecyclerView) view.findViewById(R.id.rv_category);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);
        registerForContextMenu(recyclerView);
    }

    @Override
    public void displayItem(View view, List<Category> category) {
        adapter=new CategoryAdapter(view.getContext(),category);
        recyclerView.setAdapter(adapter);
    }

    /* Events */
    @Override
    public void handleInsertEvent(View view, String message) {
        Toast.makeText(view.getContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void processDialogEnable(View view) {
        progressDialog = new ProgressDialog(view.getContext());
        progressDialog.setContentView(R.layout.activity_main);
        progressDialog.getWindow().setBackgroundDrawableResource(
                R.color.transparent
        );
        progressDialog.show();
    }

    @Override
    public void processDialogDisable() {
        progressDialog.dismiss();
    }
}