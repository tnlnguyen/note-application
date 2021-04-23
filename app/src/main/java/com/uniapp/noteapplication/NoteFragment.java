package com.uniapp.noteapplication;

import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.uniapp.noteapplication.adapter.NoteAdapter;
import com.uniapp.noteapplication.database.StatusDatabase;
import com.uniapp.noteapplication.model.INoteView;
import com.uniapp.noteapplication.model.Note;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class NoteFragment extends Fragment implements INoteView {

    /*ArrayList<Status> lst_item;
    StatusItemAdapter itemAdapter;
    RecyclerView recyclerView;
    ConstraintLayout constraintLayout;*/

    List<Note> lst_item;
    Dialog dialog;
    NoteAdapter adapter;
    RecyclerView recyclerView;

    FloatingActionButton statusPlus;
    EditText edtNote;
    TextView tvPlanDate;
    Button btnAddEdit,btnClose,btChooserDate;
    Spinner spinCategory, spinPriority, spinStatus;

    StatusDatabase userDatabase;

    String currentDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault()).format(new Date());

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_status, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);
        userDatabase= Room.databaseBuilder(view.getContext(), StatusDatabase.class, StatusDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build();

        statusPlus.setOnClickListener(v -> {
            openDialog(Gravity.CENTER,view,R.layout.dialog_note,"Status Form", true);
        });
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

        btnAddEdit = (Button) dialog.findViewById(R.id.btAdd);

        btnClose = (Button) dialog.findViewById(R.id.btClose);

        btChooserDate = (Button) dialog.findViewById(R.id.btChooseDate);

        tvPlanDate = (TextView) dialog.findViewById(R.id.tvPlanDate);

        spinCategory = (Spinner) dialog.findViewById(R.id.spinCategory);

        spinPriority = (Spinner) dialog.findViewById(R.id.spinPriority);

        spinStatus = (Spinner) dialog.findViewById(R.id.spinStatus);

        edtNote = (EditText) dialog.findViewById(R.id.edtNote);

        if(isAdd)
        {
            btnAddEdit.setText("Add");
            btnAddEdit.setOnClickListener(v ->{

            });
        }
        else
        {
            btnAddEdit.setText("Edit");
            //load du lieu do vao edit text
            btnAddEdit.setOnClickListener(v ->{

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
        statusPlus = view.findViewById(R.id.btnPlus);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        //it helps optimize your data which not be affected by content in ItemAdapter

        LinearLayoutManager layout_manager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layout_manager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(view.getContext(),layout_manager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        //adding divider into recycler view

        Note noteView = new Note();

        lst_item = noteView.initList();

        adapter = new NoteAdapter(lst_item,view.getContext());

        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean isEmpty() {
        if(TextUtils.isEmpty(edtNote.getText().toString()))
            return true;
        else
            return false;
    }
}