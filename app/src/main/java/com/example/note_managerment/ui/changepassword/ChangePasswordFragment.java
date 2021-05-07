package com.example.note_managerment.ui.changepassword;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.note_managerment.R;
import com.example.note_managerment.controller.AccountController;
import com.example.note_managerment.controller.IAccountController;
import com.example.note_managerment.view.IChangePasswordView;

import java.util.HashMap;
import java.util.Map;

public class ChangePasswordFragment extends Fragment implements IChangePasswordView {
    SharedPreferences sharedPreferences;
    EditText currentPass, newPass, passAgain;
    Button btn_change, btn_home;
    IAccountController accountController;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_changepassword, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sharedPreferences = this.getActivity().getSharedPreferences("DataLogin", Context.MODE_PRIVATE);
        accountController = new AccountController(null,null, this);

        changePassWord();
        processChangePassword();
    }

    @Override
    public void changePassWord() {
        currentPass = getView().findViewById(R.id.currentPass);
        newPass = getView().findViewById(R.id.newPass);
        passAgain = getView().findViewById(R.id.newPass);
        btn_change = getView().findViewById(R.id.btn_change);
        btn_home = getView().findViewById(R.id.btn_home);
    }

    @Override
    public void handleEvent(String message, View view) {
        Toast.makeText(view.getContext(), message, Toast.LENGTH_LONG).show();
    }

    public void processChangePassword() {
        btn_change.setOnClickListener(v -> {
            Map<String, Object> params = new HashMap<>();

            params.put("current", currentPass != null ? currentPass : "");
            params.put("new", newPass != null ? newPass : "");
            params.put("confirm", passAgain != null ? passAgain : "");
            params.put("email", sharedPreferences.getString("email", ""));

            accountController.changePassword(params);
        });
    }

    @Override
    public void savePreferences(Map<String, Object> params) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("password",(String) params.get("new"));
        editor.apply();
    }
}