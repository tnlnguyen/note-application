package com.example.note_managerment.ui.changepassword;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.example.note_managerment.R;
import com.example.note_managerment.controller.IAccountController;
import com.example.note_managerment.view.IChangePasswordView;

import static android.widget.TextView.*;

public class ChangePasswordFragment extends Fragment implements IChangePasswordView {
    SharedPreferences sharedPreferences;
    EditText currentPass, newPass, passAgain;
    Button btn_change, btn_home;
    IAccountController accountController;
    private ChangePassWordViewModel changePassWordViewModel;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        changePassWordViewModel = new ViewModelProvider(this).get(ChangePassWordViewModel.class);
        View root = inflater.inflate(R.layout.fragment_changepassword, container, false);
        changePassWordViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });
        return root;
    }

    @Override
    public void changePassWord(View view) {
        currentPass = getView().findViewById(R.id.currentPass);
        newPass = getView().findViewById(R.id.newPass);
        passAgain = getView().findViewById(R.id.newPass);
        btn_change = getView().findViewById(R.id.btn_change);
        btn_home = getView().findViewById(R.id.btn_home);



    }

    public void initVariable(){

    }

    @Override
    public void handleEvent() {

    }

    @Override
    public void handlePreferences() {

    }

    @Override
    public void handleInsertEvent(String message) {

    }

}
