package com.example.giveandtake.model;

import androidx.lifecycle.LiveData;

import java.util.List;

public class Model {
    private static final Model _instance = new Model();
    public static Model instance(){
        return _instance;
    }
    private Model(){
    }

    public LiveData<List<Post>> getAllPosts() {
        return null;
    }
}
