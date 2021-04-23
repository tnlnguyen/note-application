package com.example.note_managerment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.note_managerment.R;
import com.example.note_managerment.model.Status;
import com.example.note_managerment.viewholder.StatusViewHolder;

import java.util.List;

public class StatusAdapter extends RecyclerView.Adapter<StatusViewHolder> {
    Context context;
    List<Status> statusList;

    public StatusAdapter(Context context, List<Status> statusList) {
        this.context = context;
        this.statusList = statusList;
    }

    @NonNull
    @Override
    public StatusViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemview= LayoutInflater.from(context)
                .inflate(R.layout.fragment_status_item, parent, false);

        return new StatusViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull StatusViewHolder holder, int position) {
        holder.name.setText(statusList.get(position).getName());
        holder.date.setText(statusList.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        return statusList.size();
    }
}
