package com.example.note_managerment.adapter;

import android.app.Dialog;
import android.content.Context;
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
import com.example.note_managerment.controller.CategoryController;
import com.example.note_managerment.controller.ICategoryController;
import com.example.note_managerment.database.CategoryDatabase;
import com.example.note_managerment.model.Category;
import com.example.note_managerment.view.ICategoryView;
import com.example.note_managerment.viewholder.CategoryViewHolder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryViewHolder> implements ICategoryAdapter, ICategoryView {

    Context context;
    List<Category> categoryList;
    View view;

    public Dialog editDialog;
    TextView tvTitle;
    EditText txtCategory;
    CategoryDatabase userDatabase;
    Button closeDialog,editCategory;

    public CategoryAdapter adapter;
    public RecyclerView recyclerView;

    ICategoryController categoryController;

    String currentDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault()).format(new Date());

    public CategoryAdapter(View view,List<Category> categoryList, RecyclerView recyclerView) {
        this.view = view;
        this.categoryList = categoryList;
        this.recyclerView = recyclerView;
    }


    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemview= LayoutInflater.from(view.getContext())
                .inflate(R.layout.fragment_status_item, parent, false);

        return new CategoryViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {

        // Inflate the layout for this fragment
        categoryController  = new CategoryController(this,view);

        holder.name.setText(categoryList.get(position).getName());
        holder.date.setText(categoryList.get(position).getDate());

        holder.setItemClickListener((view, position1, isLongClick) -> {
            if(isLongClick)
            {
                //creating a popup menu

                editCategory(view,position1);

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
                            deleteCategory(view,position);
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
            }

        });
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    @Override
    public void editCategory(View view, int position) {

        editDialog = new Dialog(view.getContext());
        editDialog.setContentView(R.layout.fragment_dialog);
        editDialog.setCancelable(false);
        txtCategory = editDialog.findViewById(R.id.txtName);
        closeDialog = editDialog.findViewById(R.id.close_catelgory);
        editCategory = editDialog.findViewById(R.id.add_category);
        tvTitle = editDialog.findViewById(R.id.txtTitle);

        tvTitle.setText("Category Form");
        txtCategory.setText(categoryList.get(position).getName());

        editCategory.setText("Edit");

        editCategory.setOnClickListener(a -> {
            Map<String, Object> params = new HashMap<>();

            Category category = new Category();
            category.setId(categoryList.get(position).getId());
            category.setName(txtCategory.getText().toString());
            category.setDate(currentDate);

            params.put("category", category);

            categoryController.editCategory(params);
            categoryController.getListItem();
            editDialog.dismiss();
        });

        closeDialog.setOnClickListener(c -> {
            editDialog.dismiss();
        });

    }

    @Override
    public void deleteCategory(View view, int position) {
        Map<String, Object> params = new HashMap<>();

        Category category = new Category();
        category.setId(categoryList.get(position).getId());
        category.setName(categoryList.get(position).getName());
        category.setDate(categoryList.get(position).getDate());

        params.put("category", category);

        categoryController.deleteCategory(params);
        categoryController.getListItem();

    }

    @Override
    public void insertCategory(View view) {



    }

    @Override
    public void initVariable(View view) {

    }

    @Override
    public boolean isEmpty(String textBox) {
        return false;
    }

    @Override
    public void displayItem(View view, List<Category> category) {
        adapter=new CategoryAdapter(view,categoryList,recyclerView);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }


    @Override
    public void handleInsertEvent(String message, View view) {
        Toast.makeText(view.getContext(), message, Toast.LENGTH_LONG).show();
    }
}