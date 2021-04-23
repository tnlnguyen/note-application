package com.example.note_managerment.viewholder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.note_managerment.R;

public class StatusViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener {
    public TextView name;
    public TextView date;

    public int idStatus;
    ConstraintLayout constraintLayout_item;
    ItemClickListener itemClickListener;

    public StatusViewHolder(@NonNull View itemView) {
        super(itemView);

        name = itemView.findViewById(R.id.name_category);
        date = itemView.findViewById(R.id.date_category);

        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);

    }

    public void setItemClickListener(ItemClickListener itemClickListener)
    {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public boolean onLongClick(View v) {
        itemClickListener.onClick(v,getAdapterPosition(),true);
        return false;
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v,getAdapterPosition(),false);
    }

}
