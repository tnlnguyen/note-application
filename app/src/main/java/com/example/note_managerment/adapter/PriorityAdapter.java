package com.example.note_managerment.adapter;

import android.app.Dialog;
import android.text.TextUtils;
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
import com.example.note_managerment.controller.IPriorityController;
import com.example.note_managerment.controller.IStatusController;
import com.example.note_managerment.controller.PriorityController;
import com.example.note_managerment.controller.StatusController;
import com.example.note_managerment.database.CategoryDatabase;
import com.example.note_managerment.model.Priority;
import com.example.note_managerment.model.Status;
import com.example.note_managerment.view.IPriorityView;
import com.example.note_managerment.view.IStatusView;
import com.example.note_managerment.viewholder.PriorityViewHolder;
import com.example.note_managerment.viewholder.StatusViewHolder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class PriorityAdapter extends RecyclerView.Adapter<PriorityViewHolder> implements IPriorityAdapter, IPriorityView {
    View view;
    List<Priority> priorityList;
    public Dialog editDialog;
    TextView tvTitle;
    EditText txtPriority;
    CategoryDatabase userDatabase;
    Button closeDialog,editPriority;

    public PriorityAdapter adapter;
    public RecyclerView recyclerView;

    IPriorityController priorityController;

    String currentDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault()).format(new Date());

    public PriorityAdapter(View view, List<Priority> priorityList, RecyclerView recyclerView) {
        this.view = view;
        this.priorityList = priorityList;
        this.recyclerView = recyclerView;
    }

    @NonNull
    @Override
    public PriorityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemview= LayoutInflater.from(view.getContext())
                .inflate(R.layout.fragment_status_item, parent, false);

        return new PriorityViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull PriorityViewHolder holder, int position) {
        // Inflate the layout for this fragment
        priorityController  = new PriorityController(this,view);

        holder.name.setText(priorityList.get(position).getName());
        holder.date.setText(priorityList.get(position).getDate());



        holder.setItemClickListener((view, position1, isLongClick) -> {
            if(isLongClick)
            {
                //creating a popup menu

                editPriority(view,position1);

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
                            deletePriority(view,position);
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
                //showDialog("Status Form",null);
            }
        });
    }

    @Override
    public int getItemCount() {
        return priorityList.size();
    }

    @Override
    public void editPriority(View view,int position) {

        editDialog = new Dialog(view.getContext());
        editDialog.setContentView(R.layout.fragment_dialog);
        editDialog.setCancelable(false);
        txtPriority = editDialog.findViewById(R.id.txtName);
        closeDialog = editDialog.findViewById(R.id.close_catelgory);
        editPriority = editDialog.findViewById(R.id.add_category);
        tvTitle = editDialog.findViewById(R.id.txtTitle);

        tvTitle.setText("Priority Form");
        txtPriority.setText(priorityList.get(position).getName());

        editPriority.setText("Edit");

        editPriority.setOnClickListener(a -> {
            Map<String, Object> params = new HashMap<>();

            Priority priority = new Priority();
            priority.setId(priorityList.get(position).getId());
            priority.setName(txtPriority.getText().toString());
            priority.setDate(currentDate);

            params.put("priority", priority);

            priorityController.editPriority(params);
            priorityController.getListItem();
            notifyDataSetChanged();
            editDialog.dismiss();
        });

        closeDialog.setOnClickListener(c -> {
            editDialog.dismiss();
        });
    }

    @Override
    public void deletePriority(View view, int position) {
        Map<String, Object> params = new HashMap<>();

        Priority priority = new Priority();
        priority.setId(priorityList.get(position).getId());
        priority.setName(priorityList.get(position).getName());
        priority.setDate(priorityList.get(position).getDate());

        params.put("priority", priority);

        priorityController.deletePriority(params);
        priorityController.getListItem();
        notifyDataSetChanged();
    }

    @Override
    public void insertPriority(View view) {

    }

    @Override
    public void initVariable(View view) {

    }

    @Override
    public boolean isEmpty(String textBox) {
        if(TextUtils.isEmpty(textBox))
            return true;
        else
            return false;
    }

    @Override
    public void displayItem(View view, List<Priority> priorityList) {
        adapter=new PriorityAdapter(view,priorityList,recyclerView);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void handleInsertEvent(String message, View view) {
        Toast.makeText(view.getContext(), message, Toast.LENGTH_LONG).show();
    }

    public Priority getItem(int position)
    {
        return priorityList.get(position);
    }
}
