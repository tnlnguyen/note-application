package com.uniapp.noteapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.uniapp.noteapplication.model.Category;
import com.uniapp.noteapplication.viewholder.CategoryViewHolder;

import java.util.List;
import com.uniapp.noteapplication.R;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryViewHolder> {

    Context context;
    List<Category> categoryList;

    public CategoryAdapter(Context context, List<Category> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemview= LayoutInflater.from(context)
                .inflate(R.layout.fragment_category, parent, false);

        return new CategoryViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        holder.name.setText(categoryList.get(position).getName());
        holder.date.setText(categoryList.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }
}