package com.uniapp.noteapplication.adapter;

import android.app.Dialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.uniapp.noteapplication.model.Status;

import com.uniapp.noteapplication.R;
import com.uniapp.noteapplication.view.IStatusView;
import com.uniapp.noteapplication.viewholder.StatusViewHolder;

import java.util.List;

public class StatusAdapter extends RecyclerView.Adapter<StatusViewHolder> {
    View view;
    List<Status> statusList;
    IStatusView statusView;

    public StatusAdapter(View view, List<Status> statusList, IStatusView statusView) {
        this.view = view;
        this.statusList = statusList;
        this.statusView = statusView;
    }

    @NonNull
    @Override
    public StatusViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView= LayoutInflater.from(view.getContext())
                .inflate(R.layout.fragment_status_item, parent, false);

        return new StatusViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull StatusViewHolder holder, int position) {
        holder.name.setText(statusList.get(position).getName());
        holder.date.setText(statusList.get(position).getDate());

        holder.setItemClickListener((view, position1, isLongClick) -> {
            if(isLongClick)
            {
                //creating a popup menu
                statusView.editStatus(view,position1);

                PopupMenu popup = new PopupMenu(view.getContext(), holder.itemView);
                //inflating menu from xml resource
                popup.inflate(R.menu.option_menu);
                popup.setGravity(Gravity.RIGHT);
                //adding click listener
                popup.setOnMenuItemClickListener(item -> {
                    switch (item.getItemId()) {
                        case R.id.edit:
                            Dialog editDialog = new Dialog(view.getContext());
                            editDialog.setContentView(R.layout.fragment_dialog);
                            editDialog.show();
                            break;
                        case R.id.delete:
                            statusView.deleteStatus(view,position);
                            break;
                    }
                    return false;
                });

                //displaying the popup
                popup.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
