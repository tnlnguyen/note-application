package com.uniapp.noteapplication;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    FragmentManager fm;
    Button btnStatus,btnCategory;

    NameFeature oldFrag;
    FragmentTransaction ft_add;
    Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnStatus = findViewById(R.id.btnStatus);
        btnCategory = findViewById(R.id.btnCategory);

        fm = getSupportFragmentManager();



        btnStatus.setOnClickListener(v ->{
            initFragment(NameFeature.STATUS);
        });
        btnCategory.setOnClickListener(v ->{
            initFragment(NameFeature.NOTE);
        });

    }


    private void initFragment(NameFeature nameFeature)
    {
        replaceFragment();
        ft_add = fm.beginTransaction();

        switch (nameFeature)
        {
            case STATUS:
                fragment = new StatusFragment();
                ft_add.replace(R.id.fLayout, fragment);
                break;
            case NOTE:
                fragment = new NoteFragment();
                ft_add.replace(R.id.fLayout, new NoteFragment());
                break;
        }
        ft_add.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft_add.commit();
    }

    private void replaceFragment()
    {
        if(fragment!=null)
        {
            fm.beginTransaction().remove(fragment);
            fm.beginTransaction().commit();
            fm.beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
            fragment = null;
        }

    }
}