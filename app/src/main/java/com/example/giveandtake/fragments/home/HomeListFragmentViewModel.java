package com.example.giveandtake.fragments.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.giveandtake.model.PostModel;
import com.example.giveandtake.model.Post;

import java.util.List;

public class HomeListFragmentViewModel extends ViewModel {

    private LiveData<List<Post>> data = PostModel.instance().getAllPosts();

    public LiveData<List<Post>> getData(){
        return data;
    }
}