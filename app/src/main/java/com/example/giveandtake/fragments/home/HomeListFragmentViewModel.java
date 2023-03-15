package com.example.giveandtake.fragments.home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.giveandtake.R;
import com.example.giveandtake.model.Model;
import com.example.giveandtake.model.Post;

import java.util.List;

public class HomeListFragmentViewModel extends ViewModel {

    private LiveData<List<Post>> data = Model.instance().getAllPosts();

    LiveData<List<Post>> getData(){
        return data;
    }
}