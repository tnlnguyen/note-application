package com.uniapp.noteapplication.viewholder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.uniapp.noteapplication.R;

public class CategoryViewHolder extends RecyclerView.ViewHolder {
    public TextView name_category;
    public TextView date_category;

    public CategoryViewHolder(@NonNull View itemView) {
        super(itemView);

        name_category= itemView.findViewById(R.id.category);
        date_category= itemView.findViewById(R.id.date_category);
    }

}