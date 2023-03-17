package com.example.giveandtake.fragments.user;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.giveandtake.model.User;
import com.example.giveandtake.model.UserModel;

import java.util.List;

public class UserListFragmentViewModel extends ViewModel {
    private LiveData<List<User>> data = UserModel.instance().getAllUsers();

    LiveData<List<User>> getData(){
        return data;
    }
}
