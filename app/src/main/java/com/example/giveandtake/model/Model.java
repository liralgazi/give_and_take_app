package com.example.giveandtake.model;

import android.graphics.Bitmap;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.firestore.util.Listener;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Model {
    private Executor executor = Executors.newSingleThreadExecutor();
    private static final Model _instance = new Model();
    private FireBaseModel firebaseModel = new FireBaseModel();
    AppLocalDbRepository localDb = AppLocalDb.getAppDb();
    public static Model instance(){
        return _instance;
    }

    private Model(){
    }

    public interface Listener<T>{
        void onComplete(T data);
    }


    public LiveData<List<Post>> getAllPosts() {
        return null;
    }

    public enum LoadingState{
        LOADING,
        NOT_LOADING
    }

    final public MutableLiveData<LoadingState> EventPostListLoadingState = new MutableLiveData<LoadingState>(LoadingState.NOT_LOADING);

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
