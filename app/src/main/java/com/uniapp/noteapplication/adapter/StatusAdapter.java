package com.uniapp.noteapplication.adapter;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.uniapp.noteapplication.R;
import com.uniapp.noteapplication.dao.StatusDao;
import com.uniapp.noteapplication.database.StatusDatabase;
import com.uniapp.noteapplication.model.Status;
import com.uniapp.noteapplication.model.IStatusView;
import com.uniapp.noteapplication.viewholder.StatusViewHolder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class StatusAdapter extends RecyclerView.Adapter<StatusViewHolder>  implements IStatusView {
    Context context;
    List<Status> lst_status_view;
    Dialog dialog;

    EditText txtStatus;
    TextView txtTitle;
    Button btnAddEdit,btnClose;

    StatusDatabase userDatabase;
    StatusDao statusDao;
    int current_position;

    String currentDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault()).format(new Date());

    public StatusAdapter(List<Status> lst_item, Context context,StatusDao statusDao)
    {
        this.lst_status_view = lst_item;
        this.context = context;
        this.statusDao = statusDao;
    }

    @NonNull
    @Override
    public StatusViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.status_item,parent,false);

        return new StatusViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull StatusViewHolder holder, int position) {
        holder.txtName.setText("Name: " + lst_status_view.get(position).getName());
        holder.txtDateCreate.setText("Date Created: " + lst_status_view.get(position).getDate());
        holder.setItemClickListener((view, position1, isLongClick) -> {
            if(isLongClick)
            {
                //creating a popup menu
                PopupMenu popup = new PopupMenu(context, holder.itemView);
                //inflating menu from xml resource
                popup.inflate(R.menu.option_menu);
                popup.setGravity(Gravity.RIGHT);
                //adding click listener
                popup.setOnMenuItemClickListener(item -> {
                    this.current_position = position1;
                    switch (item.getItemId()) {
                        case R.id.edit:
                            //handle menu1 click
                            openDialog(Gravity.CENTER,view,R.layout.fragment_category_dialog,"Status Form",false);
                            notifyItemChanged(position1);
                            break;
                        case R.id.delete:
                            //handle menu2 click
                            statusDao.deleteStatus(position1);
                            Toast.makeText(view.getContext(),"Success", Toast.LENGTH_LONG).show();
                            notifyItemChanged(position1);
                            dialog.dismiss();
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
            notifyItemChanged(position1);

        });
    }

    @Override
    public int getItemCount() {
        return lst_status_view.size();
    }

    @Override
    public void openDialog(int gravity, View view, @LayoutRes int layoutResID, String title, boolean isAdd) {
        dialog = new Dialog(view.getContext());
        dialog.setContentView(layoutResID);
        dialog.setCancelable(false);
        //dialogOTPLoading alignment

        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity= Gravity.CENTER;
        window.setAttributes(windowAttributes);

        btnAddEdit = (Button) dialog.findViewById(R.id.add_category);

        btnClose = (Button) dialog.findViewById(R.id.close_catelgory);

        txtTitle = (TextView) dialog.findViewById(R.id.txtTitle);
        txtStatus = (EditText) dialog.findViewById(R.id.txt_category);

        txtTitle.setText(title);

        if(isAdd)
        {
            btnAddEdit.setText("Add");
            btnAddEdit.setOnClickListener(v ->{
                Status status= new Status();
                status.setName(txtStatus != null ? txtStatus.getText().toString() : "");
                status.setDate(currentDate);

                if(!isEmpty()) {
                    new Handler().postDelayed(() -> {

                        /* Insert user to database */
                        if(status!=null) {
                            statusDao.insertStatus(status);
                            Toast.makeText(view.getContext(),"Success", Toast.LENGTH_LONG).show();
                        }
                    },1000);
                } else {
                    Toast.makeText(view.getContext(),"Empty Fields", Toast.LENGTH_LONG).show();
                }
            });
        }
        else
        {
            btnAddEdit.setText("Edit");
            txtStatus.setText(lst_status_view.get(current_position).getName());
            //load du lieu do vao edit text
            btnAddEdit.setOnClickListener(v ->{
                if(!isEmpty()) {
                    new Handler().postDelayed(() -> {
                        Status status= new Status(txtStatus.getText().toString(),currentDate);

                        statusDao.updateStatus(status);
                        Toast.makeText(view.getContext(),"Success", Toast.LENGTH_LONG).show();

                        dialog.dismiss();

                    },1000);
                } else {
                    Toast.makeText(view.getContext(),"Empty Fields", Toast.LENGTH_LONG).show();
                }
            });
        }

        btnClose.setOnClickListener(v -> {
            //close dialog
            dialog.dismiss();
        });

        dialog.show();
    }

    @Override
    public void initView(View view) {

    }

    @Override
    public boolean isEmpty() {
        if(TextUtils.isEmpty(txtStatus.getText().toString()))
            return true;
        else
            return false;
    }



}
