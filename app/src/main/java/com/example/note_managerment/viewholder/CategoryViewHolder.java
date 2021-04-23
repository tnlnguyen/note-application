package com.example.note_managerment.viewholder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.note_managerment.R;


public class CategoryViewHolder extends RecyclerView.ViewHolder {
    public TextView name;
    public TextView date;

    public CategoryViewHolder(@NonNull View itemView) {
        super(itemView);

        name = itemView.findViewById(R.id.name_category);
        date = itemView.findViewById(R.id.date_category);
    }

}