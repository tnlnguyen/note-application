package com.uniapp.noteapplication.viewholder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.uniapp.noteapplication.R;

public class StatusViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener,View.OnClickListener  {
    public TextView txtName, txtDateCreate;
    public int idStatus;
    ConstraintLayout constraintLayout_item;
    ItemClickListener itemClickListener;

    public StatusViewHolder(@NonNull View itemView) {
        super(itemView);
        txtName = (TextView)itemView.findViewById(R.id.txtName);
        txtDateCreate = (TextView)itemView.findViewById(R.id.txtCreateDate);
        constraintLayout_item = (ConstraintLayout) itemView.findViewById(R.id.constraintlayoutStatusItem);
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
