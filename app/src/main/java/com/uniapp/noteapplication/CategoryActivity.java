package com.uniapp.noteapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.uniapp.noteapplication.adapter.CategoryAdapter;
import com.uniapp.noteapplication.dao.CategoryDao;
import com.uniapp.noteapplication.database.CategoryDatabase;
import com.uniapp.noteapplication.model.Category;
import com.uniapp.noteapplication.model.ICategoryView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CategoryActivity extends AppCompatActivity implements ICategoryView {

    CategoryAdapter adapter;
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;

    FloatingActionButton categoryPlus;
    EditText txtCategory;
    Button addCategory;

    CategoryDatabase userDatabase;

    String currentDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault()).format(new Date());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        recyclerView=findViewById(R.id.rv_category);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        initView();
        userDatabase= Room.databaseBuilder(this, CategoryDatabase.class, CategoryDatabase.DB_NAME)
            .allowMainThreadQueries()
            .build();

        categoryPlus.setOnClickListener(v -> {
            openDialog(Gravity.CENTER);
        });

    }

    /* Implements functions */
    @Override
    public void openDialog(int gravity) {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.fragment_category_dialog);
        dialog.setCancelable(false);

        addCategory = dialog.findViewById(R.id.add_category);
        addCategory.setOnClickListener(v -> {
            CategoryDao categoryDao =userDatabase.getCategoryDao();
            Category category= new Category();
            category.setName(txtCategory != null ? txtCategory.getText().toString() : "");
            category.setDate(currentDate);

            if(!isEmpty()) {
                new Handler().postDelayed(() -> {

                    /* Insert user to database */
                    if(category!=null) {
                        categoryDao.insertCategory(category);
                        Toast.makeText(getApplicationContext(),"Success", Toast.LENGTH_LONG).show();
                    }
                },1000);
            } else {
                Toast.makeText(this,"Empty Fields", Toast.LENGTH_LONG).show();
            }
        });

        dialog.show();
    }

    @Override
    public void initView() {
        categoryPlus =findViewById(R.id.floatingActionButton);
        txtCategory =findViewById(R.id.txt_category);
        addCategory =findViewById(R.id.add_category);
    }

    @Override
    public boolean isEmpty() {
        if(TextUtils.isEmpty(txtCategory.getText().toString()))
            return true;
        else
            return false;
    }
}