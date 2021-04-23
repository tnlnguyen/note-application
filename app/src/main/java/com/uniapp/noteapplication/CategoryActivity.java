package com.uniapp.noteapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.uniapp.noteapplication.adapter.CategoryAdapter;
import com.uniapp.noteapplication.controller.CategoryController;
import com.uniapp.noteapplication.controller.ICategoryController;
import com.uniapp.noteapplication.model.Category;
import com.uniapp.noteapplication.view.ICategoryView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategoryActivity extends AppCompatActivity implements ICategoryView {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        categoryController  = new CategoryController(this);

        /* Generate item on view */
        initVariable();
        categoryController.getListItem();

        /* Event initialization */
        insertCategory();
    }

    @Override
    public void insertCategory() {
        insertDialog = new Dialog(this);
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
            processDialogEnable();
            categoryController.insertCategory(params);
            categoryController.getListItem();
        });

        closeDialog.setOnClickListener(c -> {
            insertDialog.dismiss();
        });
    }

    /* Initialization functions */
    @Override
    public void initVariable() {
        categoryPlus=findViewById(R.id.floatingActionButton);
        recyclerView=findViewById(R.id.rv_category);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        registerForContextMenu(recyclerView);
    }

    @Override
    public void displayItem( List<Category> category) {
        adapter=new CategoryAdapter(this,category);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean isEmpty(String textBox) {
        if(TextUtils.isEmpty(textBox))
            return true;
        else
            return false;
    }

    /* Events */
    @Override
    public void handleInsertEvent(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void processDialogEnable() {
        progressDialog = new ProgressDialog(CategoryActivity.this);
        progressDialog.setContentView(R.layout.activity_category);
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