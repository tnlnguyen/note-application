package com.example.note_managerment.ui.changepassword;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ChangePassWordViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ChangePassWordViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is ChangePassWord fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }

}