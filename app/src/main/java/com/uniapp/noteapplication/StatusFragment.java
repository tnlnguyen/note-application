package com.uniapp.noteapplication;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
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
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.uniapp.noteapplication.adapter.StatusAdapter;
import com.uniapp.noteapplication.dao.StatusDao;
import com.uniapp.noteapplication.database.StatusDatabase;
import com.uniapp.noteapplication.model.IStatusView;
import com.uniapp.noteapplication.model.Status;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class StatusFragment extends Fragment implements IStatusView {
    /*ArrayList<Status> lst_item;
    StatusItemAdapter itemAdapter;
    RecyclerView recyclerView;
    ConstraintLayout constraintLayout;*/

    List<Status> lst_item;
    Dialog dialog;
    StatusAdapter adapter;
    RecyclerView recyclerView;

    FloatingActionButton statusPlus;
    EditText txtStatus;
    TextView txtTitle;
    Button btnAddEdit,btnClose;

    StatusDatabase userDatabase;
    StatusDao statusDao;

    String currentDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault()).format(new Date());

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_status, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        userDatabase= Room.databaseBuilder(view.getContext(), StatusDatabase.class, StatusDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build();

        initView(view);


        statusPlus.setOnClickListener(v -> {
            openDialog(Gravity.CENTER,view,R.layout.fragment_category_dialog,"Status Form", true);
        });
    }

    @Override
    public void openDialog(int gravity,View view,@LayoutRes int layoutResID, String title,boolean isAdd) {
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

                if(!isEmpty()) {
                    new Handler().postDelayed(() -> {
                        Status status= new Status(txtStatus.getText().toString(),currentDate);

                        statusDao.insertStatus(status);
                            Toast.makeText(view.getContext(),"Success", Toast.LENGTH_LONG).show();
                        initView(view);
                        dialog.dismiss();

                    },1000);
                } else {
                    Toast.makeText(view.getContext(),"Empty Fields", Toast.LENGTH_LONG).show();
                }
            });
        }
        else
        {
            btnAddEdit.setText("Edit");
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
        statusDao = userDatabase.getStatusDao();
        statusPlus = view.findViewById(R.id.btnPlus);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        //it helps optimize your data which not be affected by content in ItemAdapter

        LinearLayoutManager layout_manager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layout_manager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(view.getContext(),layout_manager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        //adding divider into recycler view

        Status status = new Status();

        lst_item = statusDao.getAllStatus();

        adapter = new StatusAdapter(lst_item,view.getContext(),statusDao);

        recyclerView.setAdapter(adapter);

    }

    @Override
    public boolean isEmpty() {
        if(TextUtils.isEmpty(txtStatus.getText().toString()))
            return true;
        else
            return false;
    }
}