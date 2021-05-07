package com.example.note_managerment.ui.category;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.note_managerment.R;
import com.example.note_managerment.adapter.CategoryAdapter;
import com.example.note_managerment.controller.CategoryController;
import com.example.note_managerment.controller.ICategoryController;
import com.example.note_managerment.database.CategoryDatabase;
import com.example.note_managerment.model.Category;
import com.example.note_managerment.view.ICategoryView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategoryFragment extends Fragment implements ICategoryView {


        public CategoryAdapter adapter;
        public RecyclerView recyclerView;
        LinearLayoutManager layoutManager;

        //* Application variables *//*
        FloatingActionButton categoryPlus;
        TextView tvTitle;
        EditText txtCategory;
        CategoryDatabase userDatabase;
        Button closeDialog,addCategory;
        public Dialog insertDialog;

        ICategoryController categoryController;

        public FragmentActivity   fragmentActivity;

        public View onCreateView(@NonNull LayoutInflater inflater,
                                 ViewGroup container, Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_status, container, false);
        }

        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            // Inflate the layout for this fragment
            categoryController  = new CategoryController(this,view, getActivity());
            //* Generate item on view *//*
            initVariable(view);
            categoryController.getListItem();
            fragmentActivity = getActivity();


            //* Event initialization *//*
            insertCategory(view);
        }
        @Override
        public void insertCategory(View view) {

            insertDialog = new Dialog(view.getContext());
            insertDialog.setContentView(R.layout.fragment_dialog);
            insertDialog.setCancelable(false);
            txtCategory = insertDialog.findViewById(R.id.txtName);
            closeDialog = insertDialog.findViewById(R.id.close_catelgory);
            addCategory = insertDialog.findViewById(R.id.add_category);
            tvTitle = insertDialog.findViewById(R.id.txtTitle);

            tvTitle.setText("Category Form");

            categoryPlus.setOnClickListener(v -> {
                insertDialog.show();
            });

            addCategory.setOnClickListener(a -> {
                Map<String, Object> params = new HashMap<>();
                params.put("category", txtCategory.getText().toString());

                categoryController.insertCategory(params);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        categoryController.getListItem();

                    }
                }, 1000);

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
        public void displayItem(View view, List<Category> category) {
            adapter=new CategoryAdapter(view,category,recyclerView, categoryController);
            recyclerView.setAdapter(adapter);

        }

        @Override
        public void handleInsertEvent(String message, View view) {
            Toast.makeText(view.getContext(), message, Toast.LENGTH_LONG).show();

        }

    @Override
    public FragmentActivity getFragmentActivity() {
        return fragmentActivity;
    }

}