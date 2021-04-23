package com.uniapp.noteapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.uniapp.noteapplication.adapter.CategoryAdapter;
import com.uniapp.noteapplication.dao.CategoryDao;
import com.uniapp.noteapplication.database.CategoryDatabase;
import com.uniapp.noteapplication.model.Category;
import com.uniapp.noteapplication.view.ICategoryView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CategoryActivity extends AppCompatActivity implements ICategoryView {

    CategoryAdapter adapter;
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;

    FloatingActionButton categoryPlus;
    EditText txtCategory;
    Button addCategory, closeDialog;

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
        generateItem();

        categoryPlus.setOnClickListener(v -> {
            open_dialog(Gravity.CENTER);
        });

        registerForContextMenu(recyclerView);




    }

//    @Override
//    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
//        super.onCreateContextMenu(menu, v, menuInfo);
//
//        getMenuInflater().inflate(R.menu.category_longclick_menu, menu);
//    }




    //create dialog
    public void open_dialog( int gravity)
    {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.fragment_category_dialog);
        dialog.setCancelable(false);

        txtCategory = dialog.findViewById(R.id.txt_category);
        Button close_dialog = dialog.findViewById(R.id.close_catelgory);
        Button add_category = dialog.findViewById(R.id.add_category);

        close_dialog.setOnClickListener(v -> {
            dialog.dismiss();
        });

        add_category.setOnClickListener(v -> {

            CategoryDatabase userDataBase = Room.databaseBuilder(this, CategoryDatabase.class, CategoryDatabase.DB_NAME)
                    .allowMainThreadQueries()
                    .build();

            CategoryDao categoryDAO = userDataBase.getCategoryDao();
            Category category = new Category();
            category.setName(txtCategory.getText().toString());
            category.setDate(currentDate);

            if (!isEmpty()) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        //insertcategory
                        if (category != null) {
                            categoryDAO.insertCategory(category);
                            Toast.makeText(getApplicationContext(), "Succes", Toast.LENGTH_LONG).show();
                            generateItem();
                            dialog.dismiss();
                        }

                    }
                }, 1000);
            } else {
                Toast.makeText(this, "Empty Fields", Toast.LENGTH_LONG).show();
            }

        });

        dialog.show();

        generateItem();

    }

    private void generateItem()
    {
        CategoryDatabase userDataBase= Room.databaseBuilder(this, CategoryDatabase.class,CategoryDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build();

        CategoryDao categoryDAO = userDataBase.getCategoryDao();
        List<Category> category = categoryDAO.getAllCategory();

        adapter=new CategoryAdapter(this,category);
        recyclerView.setAdapter(adapter);
    }

//        private void delete()
//        {
//            UserDataBase userDataBase= Room.databaseBuilder(this, UserDataBase.class,"my_db")
//                    .allowMainThreadQueries()
//                    .build();
//
//            CategoryDAO categoryDAO = userDataBase.getCategoryDao();
//            categoryDAO.deleteCategory();
//        }


    @Override
    public void openDialog(int gravity) {

    }

    @Override
    public void initView() {

        categoryPlus=findViewById(R.id.floatingActionButton);

    }

    public  boolean isEmpty()
    {
        if(TextUtils.isEmpty(txtCategory.getText().toString()))

            return true;
        else
            return  false;
    }
}