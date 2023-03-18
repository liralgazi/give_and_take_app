package com.example.giveandtake.model;

import android.graphics.Bitmap;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class PostModel {
    private Executor executor = Executors.newSingleThreadExecutor();
    private static final PostModel _instance = new PostModel();
    private FireBaseModel firebaseModel = new FireBaseModel();
    AppLocalDbRepository localDb = AppLocalDb.getAppDb();
    final public MutableLiveData<LoadingState> EventPostListLoadingState = new MutableLiveData<LoadingState>(LoadingState.NOT_LOADING);

    public static PostModel instance(){
        return _instance;
    }

    private PostModel(){
    }
    public interface Listener<T>{
        void onComplete(T data);

    }
    public enum LoadingState{
        LOADING,
        NOT_LOADING

    }
    private LiveData<List<Post>> postsList;

    public LiveData<List<Post>> getAllPosts() {
        if(postsList == null){
            postsList = localDb.postDao().getAll();
            refreshAllPosts();
        }
        return postsList;
    }

    public void refreshAllPosts(){
        EventPostListLoadingState.setValue(LoadingState.LOADING);
        // get local last update
        Long localLastUpdate = Post.getLocalLastUpdate();
        // get all updated recorde from firebase since local last update
        firebaseModel.getAllPostsSince(localLastUpdate,list->{
            executor.execute(()->{
                Log.d("TAG", " firebase return : " + list.size());
                Long time = localLastUpdate;
                for(Post post:list){
                    // insert new records into ROOM
                    localDb.postDao().insertAll(post);
                    if (time < post.getLastUpdated()){
                        time = post.getLastUpdated();
                    }
                }
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // update local last update
                Post.setLocalLastUpdate(time);
                EventPostListLoadingState.postValue(LoadingState.NOT_LOADING);
            });
        });
    }

    public void addPost(Post post, Listener<Void> listener){
        firebaseModel.addPost(post,(Void)->{
            refreshAllPosts();
            listener.onComplete(null);
        });
    }

    public void uploadImage(String name, Bitmap bitmap, Listener<String> listener) {
        firebaseModel.uploadImage(name,bitmap, listener);
    }
}
