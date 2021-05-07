package com.example.note_managerment.viewholder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.note_managerment.R;


public class NoteViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener,View.OnClickListener {
    public TextView txtName, txtCategory, txtPriority, txtStatus, txtPlanDate, txtCreatedDate;
    ConstraintLayout constraintLayout_item;
    ItemClickListener itemClickListener;

    public NoteViewHolder(@NonNull View itemView) {
        super(itemView);
        txtName = (TextView)itemView.findViewById(R.id.txtName);
        txtCategory = (TextView)itemView.findViewById(R.id.txtCategory);
        txtPriority = (TextView)itemView.findViewById(R.id.txtPriority);
        txtStatus = (TextView)itemView.findViewById(R.id.txtStatus);
        txtPlanDate = (TextView)itemView.findViewById(R.id.txtPlandate);
        txtCreatedDate = (TextView)itemView.findViewById(R.id.txtCreateDate);

        constraintLayout_item = (ConstraintLayout) itemView.findViewById(R.id.ConstraintLayoutNoteItem);
        //constraintLayout_wrapper = (ConstraintLayout) itemView.findViewById(R.id.constraintLayout_wrapper);

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
